package sd.srv.rest;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;

import sd.srv.rest.SharedGalleryResources;;

public class RESTServer {

	public static void main(String[] args) throws Exception {

		URI baseUri = UriBuilder.fromUri("http://0.0.0.0/").port(9090).build();

		ResourceConfig config = new ResourceConfig();

		config.register(SharedGalleryResources.class);
		
		HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);

		System.err.println("REST Server ready... ");
	}
}
