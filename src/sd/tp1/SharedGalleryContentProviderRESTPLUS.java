package sd.tp1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import sd.tp1.gui.GalleryContentProvider;
import sd.tp1.gui.Gui;
import sd.tp1.ws.ServerSOAP;
import sd.tp1.ws.ServerSOAPService;

public class SharedGalleryContentProviderRESTPLUS implements GalleryContentProvider{
	private static final int ACCEPTED = 200;
	private static final int TIMEOUT = 2000;
	private int roundRobinRest,roundRobinSoap, count; //variable to "randomize" writing to servers
	
	//All servers
	Map<String, WebTarget> serversRest;
	Map<String, ServerSOAP> serversSoap;
	Gui gui;	


	SharedGalleryContentProviderRESTPLUS() throws IOException {
		roundRobinRest=0; 
		roundRobinSoap=0;
		count=0;
		serversRest = new ConcurrentHashMap<String, WebTarget>();
		serversSoap = new ConcurrentHashMap<String, ServerSOAP> ();

		//Creating multicast sockets to discover availables servers
		final int port = 9000 ;
		final InetAddress address = InetAddress.getByName( "224.0.0.0" ) ;
		if( ! address.isMulticastAddress()) {
			System.out.println( "Use range : 224.0.0.0 -- 239.255.255.255");
		}
		MulticastSocket socket = new MulticastSocket() ;

		// TODO: Security system for UDP messages lost
		while (true){
			byte[] input = ("Album Server").getBytes();
			DatagramPacket packet = new DatagramPacket( input, input.length);
			packet.setAddress(address);
			packet.setPort(port);
			socket.send(packet);

			byte[] buffer = new byte[65536] ;
			DatagramPacket url_packet = new DatagramPacket( buffer, buffer.length );
			socket.setSoTimeout(TIMEOUT);
			while(true){
				try{
					socket.receive(url_packet);
					addServer(url_packet, serversRest, serversSoap);
				}catch (SocketTimeoutException e){
					//No more servers respond to client request
					break;
				}
			}
			break;
		}
		socket.close(); 
	}
	
	public WebTarget getServerRest () {
		
		if(roundRobinRest==serversRest.size()){
			roundRobinRest=0;
		}
		return (WebTarget) serversRest.values().toArray() [roundRobinRest++];
	}
	
	public ServerSOAP getServerSoap () {
		if(roundRobinSoap==serversSoap.size()){
			roundRobinSoap=0;
		}
		return (ServerSOAP) serversSoap.values().toArray() [roundRobinSoap++];
	}
	

	private void addServer(DatagramPacket url_packet, Map<String, WebTarget> map, Map<String, ServerSOAP> map2){
		try {
			String url = new String (url_packet.getData(), 0, url_packet.getLength());
			String [] v = url.split("/");
			if(v[3].equals("ServerRest")){
				ClientConfig config = new ClientConfig();
				Client client = ClientBuilder.newClient(config);
				WebTarget target = client.target(url);

				map.put(url, target);
			}
			else{
				URL wsURL = new URL(String.format("%s", url));
				ServerSOAPService service = new ServerSOAPService(wsURL);

				map2.put(url, service.getServerSOAPPort());
			}
		}

		catch (Exception e){		
		}
	}

	@Override
	public void register(Gui gui) {

	}

	@Override
	public List<Album> getListOfAlbums() {
		System.out.println("Listing all albuns");

		List<Album> lst = new ArrayList<Album>();
		List<String> aux = new ArrayList<String>();
		Iterator<WebTarget> t = serversRest.values().iterator();
		Iterator<ServerSOAP> s = serversSoap.values().iterator();
		while(t.hasNext() || s.hasNext()){
			if(t.hasNext()){


				String [] array = t.next().path("/albuns").request().accept(MediaType.APPLICATION_JSON).get(String[].class);
				for(int i=0;i<array.length;i++){
					SharedAlbum album = new SharedAlbum(array[i]);
					if(!albumExists(lst, album))
						lst.add(album);
				}
			}
			if(s.hasNext()){
				Collection<String> collection = s.next().getListAlbuns();
				aux.addAll(collection);
			}
			Iterator<String> i = aux.iterator();
			while(i.hasNext()){
				SharedAlbum album = new SharedAlbum(i.next());
				if(!albumExists(lst, album))
					lst.add(album);
			}
		}
		return lst;
	}

	private boolean albumExists(List<Album> lst, SharedAlbum album) {
		Iterator<Album> t = lst.iterator();
		while(t.hasNext()){
			if(album.getName().equals(t.next().getName()))
				return true;
		}
		return false;
	}

	@Override
	public List<Picture> getListOfPictures(Album album) {
		System.out.println(String.format("Listing all pictures (album : %s)", album.getName()));

		List<Picture> lst = new ArrayList<Picture>();
		String path = String.format("/albuns/%s", album.getName());
		try{
			Iterator<WebTarget> t = serversRest.values().iterator();
			Iterator<ServerSOAP> s = serversSoap.values().iterator();
			while(t.hasNext() || s.hasNext()){
				if(t.hasNext()){
					try{
						String [] array = t.next().path(path).request().accept(MediaType.APPLICATION_JSON).get(String[].class);
						for(int i=0;i<array.length;i++)
							lst.add(new SharedPicture(array[i]));
					}catch(javax.ws.rs.NotFoundException e){
					}

				}
				if(s.hasNext()){
					try{
						List <String> aux = new ArrayList<String>();

						Iterator<ServerSOAP> it = serversSoap.values().iterator();
						while(it.hasNext()){
							Collection<String> collection = it.next().getListPictures(album.getName());
							aux.addAll(collection);
						}
						Iterator<String> i = aux.iterator();
						while(i.hasNext()){
							lst.add(new SharedPicture(i.next()));
						}

					}
					catch (Exception e){
					}
				}
			}
		}
		catch (Exception e){
			return null;
		}

		return lst;

	}

	@Override
	public byte[] getPictureData(Album album, Picture picture) {
		System.out.println(String.format("Acessing picture %s data (album : %s)", picture.getName(), album.getName()));

		Iterator<WebTarget> t = serversRest.values().iterator();
		Iterator<ServerSOAP> s = serversSoap.values().iterator();
		
		String path = String.format("/albuns/%s/%s", album.getName(), picture.getName());
		while(t.hasNext() || s.hasNext()){
			if(t.hasNext()){
				try{
					byte [] pictureData = t.next().path(path).request().accept(MediaType.APPLICATION_OCTET_STREAM).get(byte[].class);
					if(pictureData.length>0)
						return pictureData;
				}
				catch (javax.ws.rs.NotFoundException e) {
				}
				catch(javax.ws.rs.BadRequestException e){
				}
			}
			if(s.hasNext()){
				
			}
		}
		return null;
		
	}

	@Override
	public Album createAlbum(String name) {
		System.out.println(String.format("Creating album named %s", name));
		String path = String.format("/albuns/newAlbum/%s", name);
		SharedAlbum album = new SharedAlbum(name);
		if(albumExists(getListOfAlbums(), album)){
			System.out.println("Album already exists. Album not created");
			return null;
		}
		else{
			
			if(++count%2==0){ 
				getServerSoap().createAlbum(name);
			}
			else {
				Response response = getServerRest().path(path).
				request().post(Entity.entity(name, MediaType.APPLICATION_JSON)); 
		
				if(response.getStatus()==ACCEPTED)
					return album;
				else{
					return null;
				}
			}
		}
		
		return null;
	}

	@Override
	public Picture uploadPicture(Album album, String name, byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAlbum(Album album) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deletePicture(Album album, Picture picture) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Represents a shared album.
	 */
	static class SharedAlbum implements GalleryContentProvider.Album {
		final String name;

		SharedAlbum(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	/**
	 * Represents a shared picture.
	 */
	static class SharedPicture implements GalleryContentProvider.Picture {
		final String name;

		SharedPicture(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}