package sd.srv.soap;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import sd.srv.BasicServer;

@WebService
public class ServerSOAP {

	public static final int PORT = 8081;
	private static final String DEFAULT_ALBUM_FILESYSTEM = "/home/miguel/AlbumFileSystem2";
	
	BasicServer server;


	public ServerSOAP () {
		server = new BasicServer(DEFAULT_ALBUM_FILESYSTEM);
	}
	
	@WebMethod
	public String [] getListAlbuns () {
		System.out.println("Listing all albuns");
		return server.getListAlbuns();
	}

	@WebMethod
	public String [] getListPictures (String album) {
		System.out.println(String.format("Listing all pictures (album : %s)", album));
		return server.getListPictures(album);
	}

	@WebMethod
	public byte [] getPictureData (String album, String picture){
		System.out.println(String.format("Acessing picture %s data ( album: %s)", picture, album));
		try{
			return server.getPictureData(album, picture);
		}catch(IOException e){
			return null;
		}
	}
	
	@WebMethod
	public boolean createAlbum (String name){
		System.out.println(String.format("Creating new album named %s", name));
		return server.createAlbum(name);
	}
	
	@WebMethod
	public void deleteAlbum (String album){
		System.out.println(String.format("Deleting %s", album));
		try{
			server.deleteAlbum(album);
		}catch (IOException e){
		}
	}
	
	@WebMethod
	public boolean deletePicture (String album, String picture){
		System.out.println(String.format("Deleting %s of %s",picture, album));
		try{
			server.deletePicture(album, picture);
			return true;
		}catch(IOException e){
			return false;
		}
	}
	
	@WebMethod
	public boolean uploadPicture (String album, String name, byte [] data){
		System.out.println(String.format("Uploading picture %s (album : %s)", name, album));
		try{
			server.uploadPicture(album, name, data);
			return true;
		}catch(IOException e){
			return false;
		}
	}
	
	public static void main (String [] args) throws IOException {
		//publish endpoint server
		
		String hostname = localhostAddress().toString();
		String address = String.format("http:/%s:%d/",hostname,PORT);
	
		Endpoint.publish(address, new ServerSOAP());
		System.err.println("FileServer started");

		//Creating Multicast Socket
		final InetAddress m_address = InetAddress.getByName("224.0.0.0");
		if(!m_address.isMulticastAddress()) {
			System.out.println( "Use range : 224.0.0.0 -- 239.255.255.255");
			System.exit(1);
		}

		MulticastSocket socket = new MulticastSocket(9000);
		socket.joinGroup(m_address);

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
		// TODO: Security system for UDP messages lost
		if(new String (packet.getData(), 0, packet.getLength()).equals("Album Server") ||
				new String (packet.getData(), 0, packet.getLength()).equals("SharedGallery Keep Alive")){
			
			String hostname = localhostAddress().toString();
			String address = String.format("http:/%s:%d",hostname,PORT);
			
			byte[] input = new String (address).getBytes();
			DatagramPacket reply = new DatagramPacket( input, input.length );
			reply.setAddress(packet.getAddress());
			reply.setPort(packet.getPort());
			socket.send(reply);
		}
	}
	
	/**
	 * Return the IPv4 address of the local machine that is not a loopback address if available.
	 * Otherwise, returns loopback address.
	 * If no address is available returns null.
	 */
	public static InetAddress localhostAddress() {
		try {
			try {
				Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
				while (e.hasMoreElements()) {
					NetworkInterface n = e.nextElement();
					Enumeration<InetAddress> ee = n.getInetAddresses();
					while (ee.hasMoreElements()) {
						InetAddress i = ee.nextElement();
						if (i instanceof Inet4Address && !i.isLoopbackAddress())
							return i;
					}
				}
			} catch (SocketException e) {
				// do nothing
			}
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			return null;
		}
	}
}
