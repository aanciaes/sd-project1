package sd.srv.rest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;;

public class ServerREST {

	public static final int PORT = 9090;
	
	public static void main(String[] args) throws Exception {

		URI baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(PORT).build();

		ResourceConfig config = new ResourceConfig();

		config.register(SharedGalleryResources.class);

		HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);

		System.err.println("REST Server ready... ");

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

