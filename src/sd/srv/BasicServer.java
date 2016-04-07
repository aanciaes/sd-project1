package sd.srv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Class that handles all basic operations on disk (File System)
 *
 */
public class BasicServer {

	private File basePath;

	public BasicServer () {
		this(".");
	}

	public BasicServer (String pathname){
		basePath = new File(pathname);
	}

	public String [] getListAlbuns () {
		return basePath.list();
	}

	public String [] getListPictures (String album) {
		File f = new File (basePath, album);
		return f.list();
	}

	public byte[] getPictureData(String album, String picture) throws IOException {
		String aux = String.format("%s/%s", album, picture);
		File f = new File(basePath, aux);

		return Files.readAllBytes(f.toPath());
	}

	public boolean createAlbum(String name){
		File f = new File(basePath, name);
		return f.mkdir();
	}

	public void deleteAlbum(String album) throws IOException {
		File f = new File(basePath, album);
		Files.delete(f.toPath());
	}

	public void deletePicture(String album, String picture) throws IOException {
		String aux = String.format("%s/%s", album, picture);
		File f = new File(basePath, aux);

		Files.delete(f.toPath());
	}

	public void uploadPicture(String album, String name, byte[] data) throws IOException{
		String aux = String.format("%s", album);
		File f = new File(basePath, aux);

		if(!f.exists())
			createAlbum(album);

		aux = String.format("%s/%s", album, name);
		f = new File(basePath, aux);

		FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
		fos.write(data);
		fos.close();
	}
}
