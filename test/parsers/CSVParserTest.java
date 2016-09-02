/**
 * Test class created following the TDD process.
 */
package parsers;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the CSVParser class.
 * The class responsibility is to parse a single flat Map representation 
 * of objects into a CSV formatted String of the form:
 * <p>
 * "value1,value2,value3"
 * <p>
 * The Map representation of nested objects is by '.'-separating
 * them in key hierarchies.
 * <p>
 * Example: For a JSON of the form {"attr1":"val1","attr2":{"attr1":val2,"attr2":"val3"}}
 * The expected Map should be ["attr1":"val1","attr2.attr1":val2,"attr2.attr2":"val3"]
 * <p>
 * The functionality of CSVParser should be:
 * <ul>
 * <li>Parse one single flat Map into a String.
 * </ul>
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class CSVParserTest {
	
	private CSVParser csv;
	private LinkedHashMap<String, Object> map;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		csv = new CSVParser(",");
		map = new LinkedHashMap<>();
		
		map.put("string", "test string");
		map.put("string.firstPart", "test");
		map.put("string.secondPart", "string");
		map.put("primitives.boolean", false);
		map.put("primitives.floating", 3.141592);
		map.put("null", null);
	}

	/**
	 * Test method for {@link parsers.CSVParser#parse(java.util.HashMap)}.
	 */
	@Test
	public void testMapToStringParsing() {
		String parsedStr = csv.parse(map);
		
		assertEquals("test string,test,string,false,3.141592,null", parsedStr);
	}

}
