package sd.srv.rest;

import java.net.InetAddress;
import java.net.URI;

import javax.net.ssl.HostnameVerifier;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;;

public class RESTServer {

	public static void main(String[] args) throws Exception {

		URI baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(9090).build();
		
		ResourceConfig config = new ResourceConfig();

		config.register(SharedGalleryResources.class);
		
		HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);

		System.err.println("REST Server ready... ");
	}
}
