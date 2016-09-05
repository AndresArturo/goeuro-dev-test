/**
 * Test class created following the TDD process.
 */
package parsers;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests the CSVMapParser class.
 * The class responsibility is to parse flat Maps representation 
 * of objects into a CSV formatted String of the form:
 * <p>
 * "Map1.value1,Map1.value2" + <system specific new line> +
 * "Map2.value1,Map2.value2"
 * <p>
 * The Map representation of nested objects is by '.'-separating
 * them in key hierarchies.
 * <p>
 * Example: For a JSON of the form {"attr1":"val1","attr2":{"attr1":val2,"attr2":"val3"}}
 * The expected Map should be ["attr1":"val1","attr2.attr1":val2,"attr2.attr2":"val3"]
 * <p>
 * The functionality of CSVMapParser should be:
 * <ul>
 * <li>Parse one single flat Map into a String.
 * <li>Parse a list of Maps into one total parsed String.
 * <li>Provide the necessary means to parse titles for the columns even with a paginated 
 *     parsing process.
 * </ul>
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class CSVMapParserTest {
	
	private CSVMapParser csv;
	private LinkedHashMap<String, Object> map;

	
	@Before
	public void setUp() throws Exception {
		csv = new CSVMapParser(",");
		map = new LinkedHashMap<>();
		
		map.put("string", "test string");
		map.put("string.firstPart", "test");
		map.put("string.secondPart", "string");
		map.put("primitives.boolean", false);
		map.put("primitives.floating", 3.141592);
		map.put("null", null);
	}
	

	@Test
	public void testMapToCSVString() {
		String parsedStr = csv.parseMap(map);
		
		assertEquals("test string,test,string,false,3.141592,null", parsedStr);
	}
	
	
	@Test
	public void testMapsToCSVString() {
		String parsedStr = csv.firstParsing(Arrays.asList(map,map));
		
		assertTrue(parsedStr.startsWith("string,string.firstPart,string.secondPart,"
				+ "primitives.boolean,primitives.floating,null")); //Titles well added
		assertTrue(parsedStr.contains(String.format(",null%ntest string,"))); //Line breaks (rows)
	}

}
