/**
 * Test class created following the TDD process.
 */
package parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import parsers.JSONStringParser;

/**
 * Tests the JSONStringParser class.
 * The class responsibility is to interpret a raw String as JSON to
 * parse it to a flat Map representing nested objects by '.'-separated 
 * key hierarchies.
 * <p>
 * Example: For a JSON of the form {"attr1":"val1","attr2":{"attr1":val2,"attr2":"val3"}}
 * The expected Map should be ["attr1":"val1","attr2.attr1":val2,"attr2.attr2":"val3"]
 * <p>
 * The functionality of JSONStringParser should be:
 * <ul>
 * <li>Parse a raw String into an easy-to-manipulate JSON representation.
 * <li>Parse each JSON object into a flat Map hierarchy as defined above.
 * </ul>
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class JSONStringParserTest {
	
	private JSONStringParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new JSONStringParser();
	}

	@Test
	public void testStringToJSONParsing() throws ParseException {
		List<Map<String, Object>> mappedJSONs;
		Map<String, Object> mappedJSON;
		
		mappedJSONs = parser.parseString("{\"int\":379,\"null\":null,\"string\":\"string\","
				                       + "\"bool\":true,\"float\":3.1415}");
		assertEquals(1, mappedJSONs.size());
		
		mappedJSON = mappedJSONs.get(0);
		assertEquals(379, mappedJSON.get("int")); //Parsing of JSON int numbers
		assertNull(mappedJSON.get("null")); //Parsing of JSON null values
		assertEquals("string", mappedJSON.get("string")); //Parsing of JSON strings
		assertTrue((Boolean) mappedJSON.get("bool")); //Parsing of JSON boolean values
		assertEquals(3.1415, mappedJSON.get("float")); //Parsing of JSON floating-point numbers
		
		try {
			parser.parseString("{non-compliant json string here}"); //Test correct JSON parsing
			assertTrue(false);
		} catch(ParseException e) {
			assertEquals("Error parsing the raw JSON string", e.getMessage());
		}
	}
	
	
	@Test
	public void testJSONToMapParsing() throws ParseException {
		List<Map<String, Object>> mappedJSONs;
		
		mappedJSONs = parser.parseString("[{\"_id\":376809,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.33962,\"longitude\":12.37129},\"locationId\":8982,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"pt\":\"Lípsia\",\"ru\":\"Лейпциг\",\"it\":\"Lipsia\",\"zh\":\"莱比锡\",\"cs\":\"Lipsko\",\"pl\":\"Lipsk\"},\"alternativeNames\":{\"it\":[\"Sassonia - Lipsia\"],\"es\":[\"Sajonia - Leipzig\"],\"pl\":[\"Saksonia - Lipsk\"],\"pt\":[\"Saxónia - Lípsia\"],\"fr\":[\"Saxe - Leipzig\"],\"ru\":[\"Саксония - Лейпциг\"],\"de\":[\"Sachsen - Leipzig\"],\"zh\":[\"萨克森 - 莱比锡\"],\"en\":[\"Saxony - Leipzig\"],\"cs\":[\"Sasko - Lipsko\"],\"sv\":[\"Sachsen - Leipzig\"],\"ca\":[\"Saxònia - Leipzig\"],\"nl\":[\"Saksen - Leipzig\"]}},{\"_id\":425121,\"key\":null,\"name\":\"Böhlen (Leipzig)\",\"fullName\":\"Böhlen (Leipzig), Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.20061,\"longitude\":12.38622},\"locationId\":124459,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{},\"alternativeNames\":{}},{\"_id\":314829,\"key\":null,\"name\":\"Leipzig\",\"fullName\":\"Leipzig (LEJ), Germany\",\"iata_airport_code\":\"LEJ\",\"type\":\"airport\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":51.41974,\"longitude\":12.22014},\"locationId\":null,\"inEurope\":true,\"countryId\":56,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null,\"names\":{\"it\":\"Lipsia\"},\"alternativeNames\":{}}]");
		
		assertEquals(3, mappedJSONs.size());
		
		assertEquals(12.22014, mappedJSONs.get(2).get("geo_position.longitude")); //One level of objects nesting
		assertEquals(51.20061, mappedJSONs.get(1).get("geo_position.latitude")); //One level of objects nesting
		assertEquals("Saksonia - Lipsk", mappedJSONs.get(0).get("alternativeNames.pl.0")); //One level object and array nesting
	}

}
