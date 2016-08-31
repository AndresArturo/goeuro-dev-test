package input;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpReader {
	
	
	private OkHttpClient client;
	private Request request;
	
	
	public HttpReader(String httpUrl) {
		client = new OkHttpClient();
		request = new Request.Builder()
				.url(httpUrl)
				.build();
	}


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
