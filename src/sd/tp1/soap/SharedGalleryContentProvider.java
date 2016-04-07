package sd.tp1.soap;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sd.tp1.CacheSystem;
import sd.tp1.gui.GalleryContentProvider;
import sd.tp1.gui.Gui;
import sd.tp1.ws.ServerSOAP;
import sd.tp1.ws.ServerSOAPService;

/*
 * This class provides the album/picture content to the gui/main application.
 * 
 */
public class SharedGalleryContentProvider implements GalleryContentProvider{

	public static final int TIMEOUT = 2000; 
	Gui gui;
	
	//All servers
	Map<String, ServerSOAP> servers;
	
	//cache system
	CacheSystem cache;
	
	//variable to randomize writes to servers
	int roundRobin;

	SharedGalleryContentProvider() throws IOException {
		servers = new ConcurrentHashMap<String, ServerSOAP>();
		cache = new CacheSystem();
		roundRobin=0;

		final int port = 9000 ;
		final InetAddress address = InetAddress.getByName( "224.0.0.0" ) ;
		if( ! address.isMulticastAddress()) {
			System.out.println( "Use range : 224.0.0.0 -- 239.255.255.255");
		}
		MulticastSocket socket = new MulticastSocket() ;

		connections(socket, address, port, "Album Server", servers);
		
		socket.close(); 
	}

	private void addServer (DatagramPacket url_packet, Map<String, ServerSOAP> map) {
		try {
			String url = new String (url_packet.getData(), 0, url_packet.getLength());
			URL wsURL = new URL(String.format("%s", url));

			ServerSOAPService service = new ServerSOAPService (wsURL);
			
			map.put(url, service.getServerSOAPPort());
		} catch (Exception e){
			//System.err.println("Erro " + e.getMessage());
		}
	}


	/**
	 *  Downcall from the GUI to register itself, so that it can be updated via upcalls.
	 */
	@Override
	public void register(Gui gui) {
		if( this.gui == null ) {
			this.gui = gui;

			Thread keepAlive = new Thread(new Runnable(){
				public void run(){
					while(true){
						try {
							Thread.sleep(5000);
							cache.updateCache();
							Map<String, ServerSOAP> connections = new HashMap<String, ServerSOAP>();
							
							final int port = 9000 ;
							final InetAddress address = InetAddress.getByName( "224.0.0.0" ) ;
							if( ! address.isMulticastAddress()) {
								System.out.println( "Use range : 224.0.0.0 -- 239.255.255.255");
							}
							MulticastSocket socket = new MulticastSocket() ;

							connections(socket, address, port, "SharedGallery Keep Alive", connections);
							
							socket.close(); 
							
							for(String key : servers.keySet()){
								if(!connections.containsKey(key)){
									servers.remove(key);
									System.out.println("Server Down");
									gui.updateAlbums();
								}
							}
						} catch (InterruptedException e) {
							//e.printStackTrace();
						} catch (IOException e) {
							//e.printStackTrace();
						}
					}
				}
			});
			keepAlive.start();
		}
	}

	public ServerSOAP getServer () {
		if(roundRobin>=servers.size()){
			roundRobin=0;
		}
		return (ServerSOAP) servers.values().toArray() [roundRobin++];
	}

	/**
	 * Returns the list of albums in the system.
	 * On error this method should return null.
	 */
	@Override
	public List<Album> getListOfAlbums() { 
		System.out.println("Listing all albuns");
		try{
			List<Album> lst = new ArrayList<Album>();
			List <String> aux = new ArrayList<String>();

			Iterator<ServerSOAP> it = servers.values().iterator();
			while(it.hasNext()){
				Collection<String> s = it.next().getListAlbuns();
				aux.addAll(s);
			}
			Iterator<String> i = aux.iterator();
			while(i.hasNext()){
				SharedAlbum album = new SharedAlbum(i.next());
				if(!albumExists(lst, album))
					lst.add(album);
			}
			return lst;
		}
		catch (Exception e){
			return null;
		}
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
		try{
			List<Picture> lst = new ArrayList<Picture>();
			List <String> aux = new ArrayList<String>();

			Iterator<ServerSOAP> it = servers.values().iterator();
			while(it.hasNext()){
				Collection<String> s = it.next().getListPictures(album.getName());
				aux.addAll(s);
			}
			Iterator<String> i = aux.iterator();
			while(i.hasNext()){
				lst.add(new SharedPicture(i.next()));
			}
			return lst;
		}
		catch (Exception e){
			return null;
		}
	}

	/**
	 * Returns the contents of picture in album.
	 * On error this method should return null.
	 */
	@Override
	public byte[] getPictureData(Album album, Picture picture){
		System.out.println(String.format("Acessing picture %s data (album : %s)", picture.getName(), album.getName()));
		
		Iterator<ServerSOAP> it = servers.values().iterator();
		while(it.hasNext()){
			try{
				if(cache.isInCache(picture.getName())){
					System.out.println("Cache Hit");
					return cache.getData(picture.getName());
				}
				else{
					byte [] pictureData = it.next().getPictureData(album.getName(), picture.getName());
					if(pictureData!=null){
						cache.addPicture(picture.getName(), pictureData);
						return pictureData;
					}
				}
			}catch(NullPointerException e){
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
		SharedAlbum album = new SharedAlbum(name);

		if(!albumExists(getListOfAlbums(), album) && getServer().createAlbum(name))
			return album;
		System.out.println("Album already exists. Album not created");
		return null;
	}

	/**
	 * Delete an existing album.
	 */
	@Override
	public void deleteAlbum(Album album) {
		System.out.println(String.format("Deleting album %s", album.getName()));
		try {
			Iterator<ServerSOAP> i = servers.values().iterator();
			while(i.hasNext())
				i.next().deleteAlbum(album.getName());
		}catch (Exception e){
		}
	}

	/**
	 * Add a new picture to an album.
	 * On error this method should return null.
	 */
	@Override
	public Picture uploadPicture(Album album, String name, byte[] data) {
		System.out.println(String.format("Uploading picture %s (album : %s)", name, album));
		if(getServer().uploadPicture(album.getName(), name, data)){
			cache.addPicture(name, data);
			return new SharedPicture(name);
		}
		return null;
	}

	/**
	 * Delete a picture from an album.
	 * On error this method should return false.
	 */
	@Override
	public boolean deletePicture(Album album, Picture picture) {
		System.out.println(String.format("Deleting %s of %s",picture, album));
		try{
			Iterator<ServerSOAP> i = servers.values().iterator();
			while(i.hasNext())
				try{
					i.next().deletePicture(album.getName(), picture.getName());
				}catch (NullPointerException e){
				}
			if(cache.isInCache(picture.getName()))
				cache.deletePicture(picture.getName());
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public void connections (MulticastSocket socket, InetAddress address, int port, String message, Map<String, ServerSOAP> map) throws IOException {
		byte[] input = (message).getBytes();
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
				addServer(url_packet, map);
			}catch (SocketTimeoutException e){
				//No more servers respond to client request
				break;
			}
		}
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
