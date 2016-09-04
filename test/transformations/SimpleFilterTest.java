/**
 * Test class created following the TDD process.
 */
package transformations;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests the SimpleFilter class.
 * The class responsibility is to filter unwanted attributes off of a Map.
 * <p>
 * Example: If there is a Map ["attr1":"val1","attr2.attr1":val2,"attr2.attr2":"val3"]
 * and only "attr1" and "arrt2.attr2" are wanted then the resulting Map should be
 * ["attr1":"val1","attr2.attr2":"val3"]
 * <p>
 * The functionality of SimpleFilter should be:
 * <ul>
 * <li>Create a new Map containing at most the desired attributes.
 * </ul>
 * 
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class SimpleFilterTest {

	private Map<String, Object> originalObj;
	private SimpleFilter filter;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		originalObj = new HashMap<String, Object>();
		originalObj.put("attr1", "val1");
		originalObj.put("attr2.attr2_1", "val2");
		originalObj.put("attr2.attr2_2", "val3");
		originalObj.put("attr2.attr2_3", "val4");
		originalObj.put("attr3.attr3_1.0", "val5");
		originalObj.put("attr3.attr3_1.1", "val6");
		
		filter = new SimpleFilter("attr1", "attr2.attr2_2", "attr3.attr3_1.1");
	}

	@Test
	public void testFilterAttributes() {
		Map<String, Object> result = filter.individualTransformation(originalObj);
		
		assertEquals(3, result.values().size());
		assertEquals("val1", result.get("attr1"));
		assertEquals("val3", result.get("attr2.attr2_2"));
		assertEquals("val6", result.get("attr3.attr3_1.1"));
		assertFalse(result.containsKey("attr2.attr2_1"));
	}
	
	
	@Test
	public void testExtraFilteringRule() {
		originalObj.remove("attr2.attr2_2");
		Map<String, Object> result = filter.individualTransformation(originalObj);
		
		assertEquals(2, result.values().size());
		assertEquals("val1", result.get("attr1"));
		assertEquals("val6", result.get("attr3.attr3_1.1"));
	}

}
