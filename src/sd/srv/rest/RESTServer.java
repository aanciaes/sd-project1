package sd.srv.rest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;;

public class RESTServer {

	public static void main(String[] args) throws Exception {

		URI baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(9091).build();

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
		if(new String (packet.getData(), 0, packet.getLength()).equals("Album Server")){
			byte[] input = new String ("http://localhost:9091/").getBytes();
			DatagramPacket reply = new DatagramPacket( input, input.length );
			reply.setAddress(packet.getAddress());
			reply.setPort(packet.getPort());
			socket.send(reply);
		}
	}
}

