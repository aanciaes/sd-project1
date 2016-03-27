package sd.srv.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
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

@Path("/albuns")
public class SharedGalleryResources {

	private static final String DEFAULT_ALBUM_FILESYSTEM = "/home/miguel/AlbumFileSystem2";
	private File basePath = new File (DEFAULT_ALBUM_FILESYSTEM);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAlbums() {
		System.out.println("Listing all Albuns");
		if(basePath.exists() && basePath.isDirectory()){
			return Response.ok(basePath.list()).build();				
		}else 
			return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{album}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListOfPictures(@PathParam("album") String album) {
		System.out.println(String.format("Listing all pictures ( album: %s)", album));

		File f = new File (basePath, album);

		if(f.exists() && f.isDirectory()){
			return Response.ok(f.list()).build();				
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("/{album}/{picture}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getPictureData(@PathParam("album") String album, @PathParam ("picture") String picture) throws IOException {
		System.out.println(String.format("Acessing picture %s data ( album: %s)", picture, album));

		String aux = String.format("%s/%s", album, picture);

		File f = new File (basePath, aux);

		if(f.exists()){
			return Response.ok(Files.readAllBytes(f.toPath())).build();				
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Path("/newAlbum/{album}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAlbum (@PathParam("album") String album) {
		System.out.println(String.format("Creating new album named %s", album));

		File f = new File(basePath, album);

		if(!f.exists() && f.mkdir())
			return Response.ok().build();
		else 
			return Response.status(Status.BAD_REQUEST).build();	
	}

	@DELETE
	@Path("/delete/{album}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAlbum (@PathParam("album") String album) {
		System.out.println(String.format("Deleting %s", album));

		File f = new File (basePath, album);

		try{
			Files.delete(f.toPath());
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

		String aux = String.format("%s/%s", album, picture);
		File f = new File (basePath, aux);

		try{
			Files.delete(f.toPath());
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

	@POST
	@Path("/{album}/newPicture/{picture}/{data}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response uploadPicture (@PathParam("album") String album, @PathParam("picture") String picture, @PathParam("data") String data) {
		System.out.println(String.format("Uploading picture %s (album : %s)", picture, album));

		String aux = String.format("%s", album);
		File f = new File(basePath, aux);

		if(!f.exists())
			createAlbum(album);

		aux = String.format("%s/%s", album, picture);
		f = new File(basePath, aux);
		byte [] pictureData = Base64.getUrlDecoder().decode(data);

		try{
			FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
			fos.write(pictureData);
			fos.close();
			return Response.ok().build();
		}catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
