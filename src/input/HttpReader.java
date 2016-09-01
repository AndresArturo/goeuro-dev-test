package input;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Send GET requests to an specific HTTP service and provides access
 * to the raw answer.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class HttpReader {
	
	
	private OkHttpClient client;
	private Request request;
	
	
	/**
	 * @param httpUrl The complete URL of the service to query.
	 */
	public HttpReader(String httpUrl) {
		client = new OkHttpClient();
		request = new Request.Builder()
				.url(httpUrl)
				.build();
	}


	/**
	 * Sends the GET request to the service and retrieves the response info.
	 * @return A String containing the raw answer.
	 * @throws IOException If the connection can't be established, is interrupted
	 *         or the response has an error status code.
	 */
	public String read() throws IOException {
		Response response;
		String reponseBody;
		
		response = client.newCall(request).execute();
		reponseBody = response.body().string();
		response.close();
		
		if(!response.isSuccessful())
			throw new IOException("HTTP server responded with an error status code");

		return reponseBody;
	}
	
	
	
}
