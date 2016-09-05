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
	private boolean dataLeft;
	
	
	/**
	 * @param httpUrl The complete URL of the service to query.
	 */
	public HttpReader(String httpUrl) {
		dataLeft = true;
		client = new OkHttpClient();
		request = new Request.Builder()
				.url(httpUrl)
				.build();
	}

	
	/**
	 * Sends a GET request to a HTTP service.
	 * @return The whole response not chunks.
	 * @throws IOException If the connection can't be established, 
	 *         is interrupted or the HTTP response has an error status code.
	 * @see input.Reader#read()
	 */
	@Override
	public String read() throws IOException {
		Response response;
		String responseBody;
		
		response = client.newCall(request).execute();
		responseBody = response.body().string();
		response.close();
		
		dataLeft = false;
		
		if(!response.isSuccessful()) 
			throw new IOException("HTTP server at " + request.url() + " responded with "
					+ "error status code: " + responseBody);

		return responseBody;
	}


	/**
	 * Indicates whether logically speaking there is still more data to query from
	 * the HTTP service.
	 * After a connection failure it is considered that there is still more data to read.
	 * Only after the whole data has been queried (regardless of the HTTP status code)
	 * it is considered that there is no more data left to retrieve.
	 * @return Whether there is more data to GET.
	 * @see input.Reader#isDataLeft()
	 */
	@Override
	public boolean isDataLeft() {	
		return dataLeft;
	}
	
	
	
}
