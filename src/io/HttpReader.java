package io;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HttpReader is the class responsible to send GET requests to
 * specific HTTP services and provide access to the responses.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class HttpReader implements Reader {
	
	
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


	
	/* (non-Javadoc)
	 * Fails if the connection can't be established, is interrupted
	 * or the HTTP response has an error status code.
	 * @see input.Reader#read()
	 */
	@Override
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
