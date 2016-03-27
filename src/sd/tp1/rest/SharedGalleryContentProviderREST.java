package sd.tp1.rest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import sd.tp1.gui.GalleryContentProvider;
import sd.tp1.gui.Gui;

/*
 * This class provides the album/picture content to the gui/main application.
 */
public class SharedGalleryContentProviderREST implements GalleryContentProvider{


	private static final int ACCEPTED = 200;
	private static final int TIMEOUT = 2000;
	private int roundRobin;

	List<WebTarget> servers;
	Gui gui;	


	SharedGalleryContentProviderREST() throws IOException {
		roundRobin=0;
		servers = new ArrayList<WebTarget>();

		final int port = 9000 ;
		final InetAddress address = InetAddress.getByName( "224.0.0.0" ) ;
		if( ! address.isMulticastAddress()) {
			System.out.println( "Use range : 224.0.0.0 -- 239.255.255.255");
		}
		MulticastSocket socket = new MulticastSocket() ;

		// TODO: Security system for UDP messages lost
		while (true){
			byte[] input = ("Album Server").getBytes();
			DatagramPacket packet = new DatagramPacket( input, input.length );
			packet.setAddress(address);
			packet.setPort(port);
			socket.send(packet);

			byte[] buffer = new byte[65536] ;
			DatagramPacket url_packet = new DatagramPacket( buffer, buffer.length );
			socket.setSoTimeout(TIMEOUT);
			while(true){
				try{
					socket.receive(url_packet);
					addServer(url_packet);
				}catch (SocketTimeoutException e){
					//No more servers respond to client request
					break;
				}
			}
			System.out.println(servers.size());
			break;
		}
		socket.close(); 
	}

	private void addServer (DatagramPacket url_packet) {
		try {
			String url = new String (url_packet.getData(), 0, url_packet.getLength());

			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(url);

			servers.add(target);
		} catch (Exception e){
			System.err.println("Erro " + e.getMessage());
		}
	}

	public WebTarget getServer () {
		if(roundRobin==servers.size())
			roundRobin=0;
		return servers.get(roundRobin++);
	}

	/**
	 *  Downcall from the GUI to register itself, so that it can be updated via upcalls.
	 */
	@Override
	public void register(Gui gui) {
		if( this.gui == null ) {
			this.gui = gui;
		}
	}

	/**
	 * Returns the list of albums in the system.
	 * On error this method should return null.
	 */
	@Override
	public List<Album> getListOfAlbums() {
		System.out.println("Listing all albuns");

		List<Album> lst = new ArrayList<Album>();
		Iterator<WebTarget> t = servers.iterator();
		while(t.hasNext()){
			String [] array = t.next().path("/albuns").request().accept(MediaType.APPLICATION_JSON).get(String[].class);
			for(int i=0;i<array.length;i++){
				SharedAlbum album = new SharedAlbum(array[i]);
				if(!albumExists(lst, album))
					lst.add(album);
			}
		}
		return lst;
	}

	private boolean albumExists (List<Album> lst, SharedAlbum album){
		Iterator<Album> t = lst.iterator();
		while(t.hasNext()){
			if(album.getName().equals(t.next().getName()))
				return true;
		}
		return false;
	}


	/**
	 * Returns the list of pictures for the given album. 
	 * On error this method should return null.
	 */
	@Override
	public List<Picture> getListOfPictures(Album album) {
		System.out.println(String.format("Listing all pictures (album : %s)", album.getName()));

		List<Picture> lst = new ArrayList<Picture>();
		String path = String.format("/albuns/%s", album.getName());
		Iterator<WebTarget> t = servers.iterator();
		while(t.hasNext()){
			try{
				String [] array = t.next().path(path).request().accept(MediaType.APPLICATION_JSON).get(String[].class);
				for(int i=0;i<array.length;i++)
					lst.add(new SharedPicture(array[i]));
			}catch(javax.ws.rs.NotFoundException e){
			}

		}
		if(lst.isEmpty())
			return null;
		return lst;
	}

	/**
	 * Returns the contents of picture in album.
	 * On error this method should return null.
	 */
	@Override
	public byte[] getPictureData(Album album, Picture picture) {
		System.out.println(String.format("Acessing picture %s data (album : %s)", picture.getName(), album.getName()));

		Iterator<WebTarget> t = servers.iterator();
		String path = String.format("/albuns/%s/%s", album.getName(), picture.getName());
		while(t.hasNext()){
			try{
				byte [] pictureData = t.next().path(path).request().accept(MediaType.APPLICATION_OCTET_STREAM).get(byte[].class);
				if(pictureData.length>0)
					return pictureData;
			}catch (javax.ws.rs.NotFoundException e) {

			}
		}
		return null;
	}

	/**
	 * Create a new album.
	 * On error this method should return null.
	 */
	@Override
	public Album createAlbum(String name) {
		System.out.println(String.format("Creating album named %s", name));

		String path = String.format("/albuns/newAlbum/%s", name);
		Response response = getServer().path(path).
				request().post(Entity.entity(name, MediaType.APPLICATION_JSON)); 
		if(response.getStatus()==ACCEPTED)
			return new SharedAlbum(name);
		else{
			return null;
		}
	}

	/**
	 * Delete an existing album.
	 */
	@Override
	public void deleteAlbum(Album album) {
		System.out.println(String.format("Deleting album %s", album.getName()));

		String path = String.format("/albuns/delete/%s", album.getName());
		Iterator<WebTarget> t = servers.iterator();
		while(t.hasNext()){
			t.next().path(path).request().delete();			
		}	
	}

	/**
	 * Add a new picture to an album.
	 * On error this method should return null.
	 */
	@Override
	public Picture uploadPicture(Album album, String name, byte[] data) {
		System.out.println(String.format("Uploading Picture %s (album : %s)", name, album.getName()));

		String aux = Base64.getUrlEncoder().encodeToString(data);
		String path = String.format("/albuns/%s/newPicture/%s/%s", album.getName(), name, aux);	

		Response response = getServer().path(path).request().post(Entity.entity(aux, MediaType.APPLICATION_JSON));

		if(response.getStatus()==ACCEPTED)
			return new SharedPicture(name);
		else
			return null;
	}

	/**
	 * Delete a picture from an album.
	 * On error this method should return false.
	 */
	@Override
	public boolean deletePicture(Album album, Picture picture) {
		System.out.println(String.format("Deleting picture %s (album : %s)", picture.getName(), album.getName()));

		String path = String.format("/albuns/delete/%s/%s", album.getName(), picture.getName());
		Iterator<WebTarget> t = servers.iterator();
		while(t.hasNext()){
			Response response = t.next().path(path).request().delete();

			if(response.getStatus()==ACCEPTED)
				return true;
		}
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