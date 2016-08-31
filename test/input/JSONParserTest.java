/**
 * Test class created following the TDD process.
 */
package input;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the JSON Parser class.
 * The class responsibility is to interpret a raw String as JSON to
 * parse it to a flat Map representing nested objects by '.'-separated 
 * key hierarchies.
 * <p>
 * Example: For a JSON of the form {"attr1":"val1","attr2":{"attr1":val2,"attr2":"val3"}}
 * The expected Map should be ["attr1":"val1","attr2.attr1":val2,"attr2.attr2":"val3"]
 * <p>
 * The functionality of JSON Parser should be:
 * <ul>
 * <li>Parse a raw String into an easy-to-manipulate JSON representation.
 * <li>Parse each JSON object into a flat Map hierarchy.
 * </ul>
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class JSONParserTest {
	
	private JSONParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new JSONParser();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStringToJSON() {
		parser.parse("{\"_id\":376809,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.33962,\"longitude\":12.37129},\"locationId\":8982,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"pt\":\"Lípsia\",\"ru\":\"Лейпциг\",\"it\":\"Lipsia\",\"zh\":\"莱比锡\",\"cs\":\"Lipsko\",\"pl\":\"Lipsk\"},\"alternativeNames\":{\"it\":[\"Sassonia - Lipsia\"],\"es\":[\"Sajonia - Leipzig\"],\"pl\":[\"Saksonia - Lipsk\"],\"pt\":[\"Saxónia - Lípsia\"],\"fr\":[\"Saxe - Leipzig\"],\"ru\":[\"Саксония - Лейпциг\"],\"de\":[\"Sachsen - Leipzig\"],\"zh\":[\"萨克森 - 莱比锡\"],\"en\":[\"Saxony - Leipzig\"],\"cs\":[\"Sasko - Lipsko\"],\"sv\":[\"Sachsen - Leipzig\"],\"ca\":[\"Saxònia - Leipzig\"],\"nl\":[\"Saksen - Leipzig\"]}}");
		
		assertEquals("Leipzig, Germany", parser.get("fullName"));
		assertNull(parser.get("iata_airport_code"));
		assertEquals(51.33962, parser.get("geo_position.latitude"));
		assertEquals("Saksonia - Lipsk", parser.get("alternativeNames.pl.0"));
	}
	
	
	@Test
	public void JSONToMap() {
		Map mappedJSON;
		
		parser.parse("[{\"_id\":376809,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.33962,\"longitude\":12.37129},\"locationId\":8982,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"pt\":\"Lípsia\",\"ru\":\"Лейпциг\",\"it\":\"Lipsia\",\"zh\":\"莱比锡\",\"cs\":\"Lipsko\",\"pl\":\"Lipsk\"},\"alternativeNames\":{\"it\":[\"Sassonia - Lipsia\"],\"es\":[\"Sajonia - Leipzig\"],\"pl\":[\"Saksonia - Lipsk\"],\"pt\":[\"Saxónia - Lípsia\"],\"fr\":[\"Saxe - Leipzig\"],\"ru\":[\"Саксония - Лейпциг\"],\"de\":[\"Sachsen - Leipzig\"],\"zh\":[\"萨克森 - 莱比锡\"],\"en\":[\"Saxony - Leipzig\"],\"cs\":[\"Sasko - Lipsko\"],\"sv\":[\"Sachsen - Leipzig\"],\"ca\":[\"Saxònia - Leipzig\"],\"nl\":[\"Saksen - Leipzig\"]}},{\"_id\":425121,\"key\":null,\"name\":\"Böhlen (Leipzig)\",\"fullName\":\"Böhlen (Leipzig), Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.20061,\"longitude\":12.38622},\"locationId\":124459,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{},\"alternativeNames\":{}},{\"_id\":314829,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig (LEJ), Germany\",\"iata_airport_code\":\"LEJ\",\"type\":\"airport\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.41974,\"longitude\":12.22014},\"locationId\":null,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"it\":\"Lipsia\"},\"alternativeNames\":{}}]");
		mappedJSON = parser.getMap(1);
		
		assertEquals(2, parser.mapsCount());
		
		assertEquals("Leipzig, Germany", mappedJSON.get("fullName"));
		assertNull(mappedJSON.get("iata_airport_code"));
		assertEquals(51.33962, mappedJSON.get("geo_position.latitude"));
		assertEquals("Saksonia - Lipsk", mappedJSON.get("alternativeNames.pl.0"));
	}

}
