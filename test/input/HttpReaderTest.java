/**
 * Test class created following the TDD process.
 */
package input;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import okhttp3.internal.http.HttpHeaders;
import okhttp3.mockwebserver.*;



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
	private HttpReader reader;


	@Before
	public void setUp() throws Exception {
	   server.start();
	}
	

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}
	

	@Test
	public void testSuccessfulResponse() throws IOException {
		final String body;
		MockResponse serverResponse;
		HttpResponse response;
		
		body = "[{\"_id\":376809,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.33962,\"longitude\":12.37129},\"locationId\":8982,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"pt\":\"Lípsia\",\"ru\":\"Лейпциг\",\"it\":\"Lipsia\",\"zh\":\"莱比锡\",\"cs\":\"Lipsko\",\"pl\":\"Lipsk\"},\"alternativeNames\":{\"it\":[\"Sassonia - Lipsia\"],\"es\":[\"Sajonia - Leipzig\"],\"pl\":[\"Saksonia - Lipsk\"],\"pt\":[\"Saxónia - Lípsia\"],\"fr\":[\"Saxe - Leipzig\"],\"ru\":[\"Саксония - Лейпциг\"],\"de\":[\"Sachsen - Leipzig\"],\"zh\":[\"萨克森 - 莱比锡\"],\"en\":[\"Saxony - Leipzig\"],\"cs\":[\"Sasko - Lipsko\"],\"sv\":[\"Sachsen - Leipzig\"],\"ca\":[\"Saxònia - Leipzig\"],\"nl\":[\"Saksen - Leipzig\"]}}]";
		serverResponse = new MockResponse()
				.setResponseCode(HttpURLConnection.HTTP_OK)
				.addHeader("Content-Type", "application/json;charset=utf-8")
				.setBody(body);
		
	    server.enqueue(serverResponse);	    
	    reader = new HttpReader(server.url("/api/v2/position/suggest/en/leipzig"));
	    response = reader.read();
	    
	    assertTrue(reponse.isValid());
	    assertEquals(body, reponse.getBody());
	}
	
	@Test
	public void testConnectionError() throws IOException {
		final String body;
		HttpResponse response;
		
		reader = new HttpReader(server.url("/api/v2/position/suggest/en/leipzig"));
		server.shutdown();
	    response = reader.read();
	    
	    assertFalse(reponse.isValid());
	}
	
	@Test
	public void testBadResponse() throws IOException {
		final String body;
		MockResponse serverResponse;
		HttpResponse response;
		
		body = "[{\"_id\":376809,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.33962,\"longitude\":12.37129},\"locationId\":8982,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"pt\":\"Lípsia\",\"ru\":\"Лейпциг\",\"it\":\"Lipsia\",\"zh\":\"莱比锡\",\"cs\":\"Lipsko\",\"pl\":\"Lipsk\"},\"alternativeNames\":{\"it\":[\"Sassonia - Lipsia\"],\"es\":[\"Sajonia - Leipzig\"],\"pl\":[\"Saksonia - Lipsk\"],\"pt\":[\"Saxónia - Lípsia\"],\"fr\":[\"Saxe - Leipzig\"],\"ru\":[\"Саксония - Лейпциг\"],\"de\":[\"Sachsen - Leipzig\"],\"zh\":[\"萨克森 - 莱比锡\"],\"en\":[\"Saxony - Leipzig\"],\"cs\":[\"Sasko - Lipsko\"],\"sv\":[\"Sachsen - Leipzig\"],\"ca\":[\"Saxònia - Leipzig\"],\"nl\":[\"Saksen - Leipzig\"]}}]";
		serverResponse = new MockResponse()
				.setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
				.addHeader("Content-Type", "application/json;charset=utf-8")
				.setBody(body);
		
	    server.enqueue(serverResponse);	    
	    reader = new HttpReader(server.url("/api/v2/position/suggest/en/leipzig"));
	    response = reader.read();
	    
	    assertFalse(reponse.isValid());
	    assertEquals(body, reponse.getBody());
	}
}
