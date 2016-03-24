package sd.srv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class ServerSOAP {

	private static final String DEFAULT_ALBUM_FILESYSTEM = "/home/miguel/AlbumFileSystem2";
	private File basePath;

	public ServerSOAP () {
		this(DEFAULT_ALBUM_FILESYSTEM);
	}

	public ServerSOAP (String pathname) {
		super();
		basePath = new File(pathname);
		System.out.println(basePath.toString());
	}

	@WebMethod
	public String [] listAlbums () throws FileNotFoundException {
		System.out.println("Listing all albums");
		if(basePath.exists() && basePath.isDirectory()){
			return basePath.list();		//TODO: return only directories				
		}else {
			//throw new FileNotFoundException ("File not found :" + basePath);
			return null;
		}
	}

	@WebMethod
	public String [] listPictures (String album) throws FileNotFoundException {
		System.out.println(String.format("Listing all pictures of %s album", album));

		File f = new File (basePath, album);
		if(f.exists() && f.isDirectory()){
			return f.list();		//TODO: return only files				
		}else {
			return null;
		}
	}

	@WebMethod
	public byte[] getPictureData(String album, String picture) throws IOException{
		System.out.println(String.format("Acessing image %s of %s album", picture, album));

		String aux = String.format("%s/%s", album, picture);
		File f = new File(basePath, aux);

		if(f.exists())
			return Files.readAllBytes(f.toPath());
		else
			return null;
			//throw new FileNotFoundException();
	}

	@WebMethod
	public boolean createAlbum(String name) {
		System.out.println(String.format("Creating new album named %s", name));

		File f = new File(basePath, name);

		if(!f.exists())
			return f.mkdir();
		else 
			return false;
	}

	@WebMethod
	public void deleteAlbum(String album) throws IOException {
		System.out.println(String.format("Deleting album %s", album));

		File f = new File(basePath, album);
		try{
			Files.delete(f.toPath());
		}catch(NoSuchFileException e){
			//e.printStackTrace();
		}catch(DirectoryNotEmptyException e){
			System.out.println("Directory is not empty. Album not deleted!");
			//e.printStackTrace();
		}
	}

	@WebMethod
	public boolean uploadPicture(String album, String name, byte[] data) {
		// TODO: contact servers to add picture name with contents data 
		System.out.println(String.format("Creating new picture %s in %s album", name, album));
		try{
			String aux = String.format("%s", album);
			File f = new File(basePath, aux);
			if(!f.exists())
				createAlbum(album);
			
			aux = String.format("%s/%s", album, name);
			f = new File(basePath, aux);
			
			FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
			fos.write(data);
			fos.close();
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@WebMethod
	public void deletePicture(String album, String picture) throws IOException {
		System.out.println(String.format("Deleting Picture %s of %s album", picture, album));

		String aux = String.format("%s/%s", album, picture);
		File f = new File(basePath, aux);
		try{
			Files.delete(f.toPath());
		}catch(NoSuchFileException e){
			//e.printStackTrace();
		}
	}

	public static void main (String [] args) throws IOException {
		//publish endpoint server
		Endpoint.publish("http://localhost:8081/FileServer", new ServerSOAP());
		System.err.println("FileServer started");

		//Creating Multicast Socket
		final InetAddress address = InetAddress.getByName("224.0.0.0");
		if(!address.isMulticastAddress()) {
			System.out.println( "Use range : 224.0.0.0 -- 239.255.255.255");
			System.exit(1);
		}

		MulticastSocket socket = new MulticastSocket(9000);
		socket.joinGroup(address);

		//Waiting for a client request
		while(true) {
			byte[] buffer = new byte[65536];
			DatagramPacket packet = new DatagramPacket( buffer, buffer.length );
			socket.receive(packet);
			processMessage (packet, socket);
		}
	}

	/**
	 * Processing client request
	 * @param packet Packet containing the request
	 * @param socket Muitcast Socket to reply to client
	 * @throws IOException
	 */
	public static void processMessage (DatagramPacket packet, MulticastSocket socket) throws IOException {	
		if(new String (packet.getData(), 0, packet.getLength()).equals("Album Server")){
			byte[] input = new String ("http://localhost:8081/FileServer").getBytes();
			DatagramPacket reply = new DatagramPacket( input, input.length );
			reply.setAddress(packet.getAddress());
			reply.setPort(packet.getPort());
			socket.send(reply);
		}
	}
}
