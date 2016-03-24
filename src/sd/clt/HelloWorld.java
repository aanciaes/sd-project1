package sd.clt;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import sd.clt.ws.ServerSOAP;
import sd.clt.ws.ServerSOAPService;


public class HelloWorld {

	public static void main (String [] args) throws IOException {
		if (args.length != 0){
			System.out.println("Use java HelloWorld");
			System.exit(0);
		}
		
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
			packet.setAddress(address) ;
			packet.setPort(port) ;
			socket.send(packet) ;

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
			
			ServerSOAP server = service.getServerSOAPPort();
			
			List <String> aux = server.listAlbums();
			Iterator<String> i = aux.iterator();
			while(i.hasNext()){
				System.out.println(i.next());
			}
		
		} catch (Exception e){
			System.err.println("Erro " + e.getMessage());
		}
	}
	
}
