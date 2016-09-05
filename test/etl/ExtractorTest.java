/**
 * Test class created following the TDD process.
 */
package etl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.HttpReader;
import io.Reader;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import parsers.JSONStringParser;
import java.util.Map;
import java.util.List;


/**
 * Tests the Extractor class.
 * The class responsibility is extract information from a given source.
 * <p>
 * The functionality of Extractor should be:
 * <ul>
 * <li>Extract data through a process of reading and parsing.
 * <li>Provide a mechanism of pagination. 
 * </ul>
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class ExtractorTest {
	
	private  MockWebServer server;

	@Before
	public void setUp() throws Exception {
		MockResponse serverResponse;
		server = new MockWebServer();
		
	    serverResponse = new MockResponse()
				.setResponseCode(HttpURLConnection.HTTP_OK)
				.addHeader("Content-Type", "application/json;charset=utf-8")
				.setBody("{\"int\":379,\"null\":null,\"string\":\"string\",\"bool\":true,\"float\":3.1415}");
		
	    server.enqueue(serverResponse);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	
	
	@Test
	public void testSuccesfulExtraction() {
		HttpReader reader = new HttpReader(server.url("http-service-url").toString());
		Extractor extractor = new Extractor(reader, new JSONStringParser(), 1, 1);
		
		assertTrue(extractor.canExtract());
		assertNotNull(extractor.extract());
		assertFalse(extractor.canExtract());
	}
	
	
	/*
	 * Due to limitations of the MockServer to be rebooted this test case is intended to be carried out
	 * manually by starting it with no access to Internet and then having 20 seconds to regain physical
	 * access.
	 */
	@Test
	public void testConnectionErrorHandling() throws IOException, InterruptedException, ExecutionException {
		Future<List<Map<String,Object>>> mapsFuture;
		HttpReader reader1 = new HttpReader("http://api.goeuro.com/api/v2/position/suggest/en/leipzig");
		HttpReader reader2 = new HttpReader("http://api.goeuro.com/api/v2/position/suggest/en/leipzig");
		Extractor extractor1 = new Extractor(reader1, new JSONStringParser(), 1, 1000); //Non-resilient extractor
		Extractor extractor2 = new Extractor(reader2, new JSONStringParser(), 3, 10 * 1000);//Resilient extractor
		
		
		mapsFuture = Executors.newCachedThreadPool()  //Starts to query the server before it is UP
								.submit(()->extractor2.extract()); //so connections can't be made
		
		assertNull(extractor1.extract()); //Fatal server crash
		assertNotNull(mapsFuture.get()); //Extractor should be resilient enough to recover and return a correct answer
	}
	
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testPagination() {
		Reader reader = new Reader() {  // A test Reader that provides pagination
			int pagesI = 2;
			String page = "{\"int\":379,\"null\":null,\"string\":\"string\",\"bool\":true,\"float\":3.1415}";
			String pages[] = {page,page,page};
			
			@Override
			public String read() throws IOException {
				return pages[pagesI--];
			}
			
			@Override
			public boolean isDataLeft() {
				return pagesI >= 0;
			}
		};
		
		Extractor extractor = new Extractor(reader, new JSONStringParser(), 3, 1000);
		
		assertTrue(extractor.canExtract());
		assertNotNull(extractor.extract());
		assertTrue(extractor.canExtract());  //Test that the three pages are retrieved 
		assertNotNull(extractor.extract());
		assertTrue(extractor.canExtract());
		assertNotNull(extractor.extract());
		
		assertFalse(extractor.canExtract());  //Test non-redundancy after there is no more
		extractor.extract();	             //to extract (Exception expected)
	}


}
