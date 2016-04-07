package sd.srv.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.util.Base64;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import sd.srv.BasicServer;

/**
 * Rest web service resources
 */
@Path("/albuns")
public class SharedGalleryResources {

	//Basic server handles operations on disk (File System)
	private BasicServer server = new BasicServer("/home/miguel/AlbumFileSystem");

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAlbums() {
		System.out.println("Listing all Albuns");
		
		String [] albuns = server.getListAlbuns();
		if(albuns!=null)
			return Response.ok(albuns).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{album}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListOfPictures(@PathParam("album") String album) {
		System.out.println(String.format("Listing all pictures ( album: %s)", album));

		String [] pictures = server.getListPictures(album);

		if(pictures!=null){
			return Response.ok(pictures).build();				
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/{album}/{picture}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getPictureData(@PathParam("album") String album, @PathParam ("picture") String picture){
		System.out.println(String.format("Acessing picture %s data ( album: %s)", picture, album));

		try{
			return Response.ok(server.getPictureData(album, picture)).build();				
		}catch(FileNotFoundException e){
			return Response.status(Status.NOT_FOUND).build();
		}catch(IOException e){
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Path("/newAlbum/{album}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAlbum (@PathParam("album") String album) {
		System.out.println(String.format("Creating new album named %s", album));

		if(server.createAlbum(album))
			return Response.ok().build();
		else 
			return Response.status(Status.BAD_REQUEST).build();	
	}

	@DELETE
	@Path("/delete/{album}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAlbum (@PathParam("album") String album) {
		System.out.println(String.format("Deleting %s", album));

		try{
			server.deleteAlbum(album);
			return Response.ok().build();
		}catch (FileNotFoundException e){
			return Response.status(Status.NOT_FOUND).build();
		}catch(DirectoryNotEmptyException e){
			System.out.println("Album not empty. Album not deleted");
			return Response.status(Status.BAD_REQUEST).build();
		}catch (Exception e){
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@DELETE
	@Path("/delete/{album}/{picture}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePicture (@PathParam("album") String album, @PathParam("picture") String picture) {
		System.out.println(String.format("Deleting %s of %s",picture, album));

		try{
			server.deletePicture(album, picture);
			return Response.ok().build();
		}catch (FileNotFoundException e){
			return Response.status(Status.NOT_FOUND).build();
		}catch (Exception e){
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Path("/{album}/newPicture/{picture}/{data}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response uploadPicture (@PathParam("album") String album, @PathParam("picture") String picture, @PathParam("data") String data) {
		System.out.println(String.format("Uploading picture %s (album : %s)", picture, album));

		//Picture data is uploaded encoded in Base64 as a URL String. Decoding needed
		byte [] pictureData = Base64.getUrlDecoder().decode(data);

		try{
			server.uploadPicture(album, picture, pictureData);
			return Response.ok().build();
		}catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
