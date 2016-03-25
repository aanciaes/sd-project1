package sd.tp1;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import sd.tp1.gui.GalleryContentProvider;
import sd.tp1.gui.Gui;

/*
 * This class provides the album/picture content to the gui/main application.
 */
public class SharedGalleryContentProviderREST implements GalleryContentProvider{

	Gui gui;	
	ClientConfig config;
	Client client;
	WebTarget target;
	
	
	SharedGalleryContentProviderREST() {
		// TODO: code to do when shared gallery starts
		config = new ClientConfig();
		client = ClientBuilder.newClient(config);
		target = client.target(getBaseURI());
	}
	
	private static URI getBaseURI() {
	    return UriBuilder.fromUri("http://localhost:9090/").build();
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
		List<Album> lst = new ArrayList<Album>();
	    String [] array = target.path("/Albuns")
	    		.request()
	    		.accept(MediaType.APPLICATION_JSON)
	    		.get(String[].class);
		for(int i=0;i<array.length;i++)
			lst.add(new SharedAlbum(array[i]));
		return lst;
	}


	/**
	 * Returns the list of pictures for the given album. 
	 * On error this method should return null.
	 */
	@Override
	public List<Picture> getListOfPictures(Album album) {
		List<Picture> lst = new ArrayList<Picture>();
	    String path = String.format("/Albuns/%s", album.getName());
		String [] array = target.path(path)
	    		.request()
	    		.accept(MediaType.APPLICATION_JSON)
	    		.get(String[].class);
		for(int i=0;i<array.length;i++)
			lst.add(new SharedPicture(array[i]));
		return lst;
	}

	/**
	 * Returns the contents of picture in album.
	 * On error this method should return null.
	 */
	@Override
	public byte[] getPictureData(Album album, Picture picture) {
		// TODO: obtain remote information 
		return null;
	}

	/**
	 * Create a new album.
	 * On error this method should return null.
	 */
	@Override
	public Album createAlbum(String name) {
		// TODO: contact servers to create album 
		return new SharedAlbum(name);
	}

	/**
	 * Delete an existing album.
	 */
	@Override
	public void deleteAlbum(Album album) {
		// TODO: contact servers to delete album 
	}

	/**
	 * Add a new picture to an album.
	 * On error this method should return null.
	 */
	@Override
	public Picture uploadPicture(Album album, String name, byte[] data) {
		// TODO: contact servers to add picture name with contents data 
		return new SharedPicture(name);
	}

	/**
	 * Delete a picture from an album.
	 * On error this method should return false.
	 */
	@Override
	public boolean deletePicture(Album album, Picture picture) {
		// TODO: contact servers to delete picture from album 
		return true;
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