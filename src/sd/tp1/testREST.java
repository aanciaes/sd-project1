package sd.tp1;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class testREST {

	public static void main(String [] args) throws IOException {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(getBaseURI());

		String [] s = target.path("/Albuns").request()
				.accept(MediaType.APPLICATION_JSON).get(String[].class);
		for(int i = 0; i<s.length;i++)
			System.out.println(s[i]);
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:9090/").build();
	}

}
