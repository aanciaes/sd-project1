package sd.srv.rest;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/Albuns")
public class SharedGalleryResources {

	private static final String DEFAULT_ALBUM_FILESYSTEM = "/home/miguel/AlbumFileSystem";
	private File basePath = new File (DEFAULT_ALBUM_FILESYSTEM);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAlbums() {
		System.err.printf("listAlbuns()\n");
		if(basePath.exists() && basePath.isDirectory()){
			return Response.ok(basePath.list()).build();				
		}else 
			return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/{album}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListOfPictures(@PathParam("album") String album) {
		System.err.printf("getList of Pictres( album: %s)", album);
		
		File f = new File (basePath, album);
		
		if(f.exists() && f.isDirectory()){
			return Response.ok(f.list()).build();				
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
