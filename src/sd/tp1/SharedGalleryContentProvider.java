package sd.tp1;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import sd.clt.ws.FileNotFoundException_Exception;
import sd.clt.ws.IOException_Exception;
import sd.clt.ws.ServerSOAP;
import sd.clt.ws.ServerSOAPService;
import sd.tp1.gui.GalleryContentProvider;
import sd.tp1.gui.Gui;

/*
 * This class provides the album/picture content to the gui/main application.
 * 
 * Project 1 implementation should complete this class. 
 */
public class SharedGalleryContentProvider implements GalleryContentProvider{

	Gui gui;	
	ServerSOAP server;

	SharedGalleryContentProvider() throws IOException {
		// TODO: code to do when shared gallery starts
		final int port = 9000 ;
		final InetAddress address = InetAddress.getByName( "224.0.0.0" ) ;
		if( ! address.isMulticastAddress()) {
			System.out.println( "Use range : 224.0.0.0 -- 239.255.255.255");
		}
		MulticastSocket socket = new MulticastSocket() ;

		String url="";

		while (true){ //s.setSOTimeout Socket time out exception
			byte[] input = ("Album Server").getBytes();
			DatagramPacket packet = new DatagramPacket( input, input.length );
			packet.setAddress(address);
			packet.setPort(port);
			socket.send(packet);

			byte[] buffer = new byte[65536] ;
			DatagramPacket url_packet = new DatagramPacket( buffer, buffer.length );
			socket.receive(url_packet);
			url = new String (url_packet.getData(), 0, url_packet.getLength());
			break;
		}
		socket.close(); 

		try {
			URL wsURL = new URL(String.format("%s", url));

			ServerSOAPService service = new ServerSOAPService (wsURL);

			server = service.getServerSOAPPort();
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

	/**
	 * Returns the list of albums in the system.
	 * On error this method should return null.
	 */
	@Override
	public List<Album> getListOfAlbums() { 
		try{
			List<Album> lst = new ArrayList<Album>();
			List <String> aux = server.listAlbums();
			Iterator<String> i = aux.iterator();
			while(i.hasNext()){
				lst.add(new SharedAlbum(i.next()));
			}
			return lst;
		}
		catch (Exception e){
			return null;
		}
	}

	/**
	 * Returns the list of pictures for the given album. 
	 * On error this method should return null.
	 */
	@Override
	public List<Picture> getListOfPictures(Album album) {
		try{
			List<Picture> lst = new ArrayList<Picture>();
			List <String> aux = server.listPictures(album.getName());
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
		try{
			return server.getPictureData(album.getName(), picture.getName());
		}catch (Exception e) {
			return null;
		}
	}

	/**
	 * Create a new album.
	 * On error this method should return null.
	 */
	@Override
	public Album createAlbum(String name) {
		// TODO: contact servers to create album
		if(!server.createAlbum(name)){
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
			server.deleteAlbum(album.getName());
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
		if(server.uploadPicture(album.getName(), name, data)){
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
			server.deletePicture(album.getName(), picture.getName());
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
