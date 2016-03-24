package sd.tp1;

import java.io.FileNotFoundException;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sd.clt.ws.IOException_Exception;
import sd.clt.ws.ServerSOAP;
import sd.clt.ws.ServerSOAPService;
import sd.tp1.gui.GalleryContentProvider;
import sd.tp1.gui.GalleryContentProvider.Album;
import sd.tp1.gui.Gui;

/*
 * This class provides the album/picture content to the gui/main application.
 * 
 * Project 1 implementation should complete this class. 
 */
public class SharedGalleryContentProvider implements GalleryContentProvider{

	Gui gui;
	List<ServerSOAP> servers;
	//ServerSOAP server;

	private int roundRobin;

	SharedGalleryContentProvider() throws IOException {
		// TODO: code to do when shared gallery starts
		roundRobin=0;
		servers = new ArrayList<ServerSOAP>();

		final int port = 9000 ;
		final InetAddress address = InetAddress.getByName( "224.0.0.0" ) ;
		if( ! address.isMulticastAddress()) {
			System.out.println( "Use range : 224.0.0.0 -- 239.255.255.255");
		}
		MulticastSocket socket = new MulticastSocket() ;

		String url="";

		while (true){
			byte[] input = ("Album Server").getBytes();
			DatagramPacket packet = new DatagramPacket( input, input.length );
			packet.setAddress(address);
			packet.setPort(port);
			socket.send(packet);

			byte[] buffer = new byte[65536] ;
			DatagramPacket url_packet = new DatagramPacket( buffer, buffer.length );
			socket.setSoTimeout(3000);
			while(true){
				try{
					socket.receive(url_packet);
					addServer(url_packet);
				}catch (SocketTimeoutException e){
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
			URL wsURL = new URL(String.format("%s", url));

			ServerSOAPService service = new ServerSOAPService (wsURL);
			servers.add(service.getServerSOAPPort());
			//server = service.getServerSOAPPort();
		} catch (Exception e){
			System.err.println("Erro " + e.getMessage());
		}
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

	public ServerSOAP getServer () {
		if(roundRobin==servers.size())
			roundRobin=0;
		return servers.get(roundRobin++);
	}

	/**
	 * Returns the list of albums in the system.
	 * On error this method should return null.
	 */
	@Override
	public List<Album> getListOfAlbums() { 
		try{
			List<Album> lst = new ArrayList<Album>();
			List <String> aux = new LinkedList<String>();

			Iterator<ServerSOAP> it = servers.iterator();
			while(it.hasNext()){
				try{
					Collection<String> s = it.next().listAlbums();
					aux.addAll(s);
				}catch(NullPointerException e){
				}

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
		try{
			List<Picture> lst = new ArrayList<Picture>();
			List <String> aux = new LinkedList<String>();

			Iterator<ServerSOAP> it = servers.iterator();
			while(it.hasNext()){
				try{
					Collection<String> s = it.next().listPictures(album.getName());
					aux.addAll(s);
				}
				catch(NullPointerException e){
				}
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
		// TODO: obtain remote information
		Iterator<ServerSOAP> it = servers.iterator();
		while(it.hasNext()){
			try{
				byte [] pictureData = it.next().getPictureData(album.getName(), picture.getName());
				if(pictureData.length>0)
					return pictureData; 
			}catch(NullPointerException e){
			}
			catch (IOException_Exception e) {
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
		// TODO: contact servers to create album
		if(!getServer().createAlbum(name)){
			return null;
		}
		return new SharedAlbum(name);
	}

	/**
	 * Delete an existing album.
	 */
	@Override
	public void deleteAlbum(Album album) {
		// TODO: contact servers to delete album 
		try {
			Iterator<ServerSOAP> i = servers.iterator();
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
		// TODO: contact servers to add picture name with contents data
		if(getServer().uploadPicture(album.getName(), name, data)){
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
		// TODO: contact servers to delete picture from album
		try{
			Iterator<ServerSOAP> i = servers.iterator();
			while(i.hasNext())
				i.next().deletePicture(album.getName(), picture.getName());
			return true;
		}catch (Exception e) {
			return false;
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
