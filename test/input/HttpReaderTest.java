/**
 * Test class created following the TDD process.
 */
package input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;



/**
 * Tests the HTTP Reader class.
 * The class responsibility is to read raw data from Get petitions.
 * <p>
 * The functionality of HTTP Reader should be:
 * <ul>
 * <li>Carry out GET requests appropriately.
 * <li>Give access to the response body as a String.
 * </ul>
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class HttpReaderTest {
	
	private final MockWebServer server = new MockWebServer();
	private final String serviceURL = "/api/v2/position/suggest/en/leipzig";
	private HttpReader reader;


	@Before
	public void setUp() throws Exception {
	   server.start();
	   reader = new HttpReader(server.url(serviceURL).toString());
	}
	

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}
	

	@Test
	public void testSuccessfulResponse() throws IOException {
		final String body = "[{\"_id\":376809,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.33962,\"longitude\":12.37129},\"locationId\":8982,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"pt\":\"Lípsia\",\"ru\":\"Лейпциг\",\"it\":\"Lipsia\",\"zh\":\"莱比锡\",\"cs\":\"Lipsko\",\"pl\":\"Lipsk\"},\"alternativeNames\":{\"it\":[\"Sassonia - Lipsia\"],\"es\":[\"Sajonia - Leipzig\"],\"pl\":[\"Saksonia - Lipsk\"],\"pt\":[\"Saxónia - Lípsia\"],\"fr\":[\"Saxe - Leipzig\"],\"ru\":[\"Саксония - Лейпциг\"],\"de\":[\"Sachsen - Leipzig\"],\"zh\":[\"萨克森 - 莱比锡\"],\"en\":[\"Saxony - Leipzig\"],\"cs\":[\"Sasko - Lipsko\"],\"sv\":[\"Sachsen - Leipzig\"],\"ca\":[\"Saxònia - Leipzig\"],\"nl\":[\"Saksen - Leipzig\"]}}]";
		MockResponse serverResponse;
		
		serverResponse = new MockResponse()
				.setResponseCode(HttpURLConnection.HTTP_OK)
				.addHeader("Content-Type", "application/json;charset=utf-8")
				.setBody(body);
		
	    server.enqueue(serverResponse);
	    
	    assertEquals(body, reader.read());
	}
	
	
	@Test(expected=IOException.class)
	public void testConnectionError() throws IOException {
		server.shutdown();
	    reader.read();
	}
	
	
	@Test
	public void testBadResponse() throws IOException {
		MockResponse serverResponse;
		
		serverResponse = new MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND);
	    server.enqueue(serverResponse);
	    
	    try {
	    	reader.read();
	    	assertTrue(false);
	    } catch (IOException e) {
			assertEquals("HTTP server responded with an error status code", e.getMessage());
		}
	}
	
	
	/**
	 * Tests the query of a real HTTP service.
	 * Appends a real server's hostname to the simulated service URL to make a real query.
	 * Uncomment to execute test only when the mock server is not enough.
	 * @throws IOException 
	 */
//	@Test
//	public void testRealRequest() throws IOException {
//		final String body = "[{\"_id\":376809,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.33962,\"longitude\":12.37129},\"locationId\":8982,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"pt\":\"Lípsia\",\"ru\":\"Лейпциг\",\"it\":\"Lipsia\",\"zh\":\"莱比锡\",\"cs\":\"Lipsko\",\"pl\":\"Lipsk\"},\"alternativeNames\":{\"it\":[\"Sassonia - Lipsia\"],\"es\":[\"Sajonia - Leipzig\"],\"pl\":[\"Saksonia - Lipsk\"],\"pt\":[\"Saxónia - Lípsia\"],\"fr\":[\"Saxe - Leipzig\"],\"ru\":[\"Саксония - Лейпциг\"],\"de\":[\"Sachsen - Leipzig\"],\"zh\":[\"萨克森 - 莱比锡\"],\"en\":[\"Saxony - Leipzig\"],\"cs\":[\"Sasko - Lipsko\"],\"sv\":[\"Sachsen - Leipzig\"],\"ca\":[\"Saxònia - Leipzig\"],\"nl\":[\"Saksen - Leipzig\"]}},{\"_id\":425121,\"key\":null,\"name\":\"Böhlen (Leipzig)\",\"fullName\":\"Böhlen (Leipzig), Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.20061,\"longitude\":12.38622},\"locationId\":124459,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{},\"alternativeNames\":{}},{\"_id\":314829,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig (LEJ), Germany\",\"iata_airport_code\":\"LEJ\",\"type\":\"airport\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.41974,\"longitude\":12.22014},\"locationId\":null,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"it\":\"Lipsia\"},\"alternativeNames\":{}}]";
//		   
//	    reader = new HttpReader("http://api.goeuro.com" + serviceURL);
//	    
//	    assertEquals(body, reader.read());
//	}
}
