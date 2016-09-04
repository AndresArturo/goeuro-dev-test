/**
 * Test class created following the TDD process.
 */
package transformations;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the AttributesSort class.
 * The class responsibility is to rearrange the attributes of a Map
 * given a static predefined order of them.
 * <p>
 * Example: If there is a Map ["attr1":"val1","attr2.attr1":val2,"attr2.attr2":"val3"]
 * and the order wanted is "arrt2.attr1"->"arrt2.attr2"->"attr1" then the resulting Map
 * should be ["attr1":"val1","attr2.attr2":"val3"]
 * <p>
 * The functionality of AttributesSort should be:
 * <ul>
 * <li>Order the attributes of a Map producing a new one.
 * </ul>
 * 
 * @author Andres Arturo Sanchez Dorantes
 */
public class AttributesSortingTest {

	private Map<String, Object> originalObj;
	private List<String> orderedKeys;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		originalObj = new LinkedHashMap<String, Object>();
		originalObj.put("attr1", "val1");
		originalObj.put("attr2.attr2_1", "val2");
		originalObj.put("attr2.attr2_2", "val3");
		originalObj.put("attr2.attr2_3", "val4");
		originalObj.put("attr3.attr3_1.0", "val5");
		originalObj.put("attr3.attr3_1.1", "val6");
		
		orderedKeys = Arrays.asList("attr2.attr2_2","attr2.attr2_3",
                                    "attr2.attr2_1","attr3.attr3_1.0",
                                    "attr1", "attr3.attr3_1.1");
	}
	
	
	@Test
	public void testStaticOrdering() {
		Map<String, Object> orderedMap = new AttributesSort(orderedKeys).transformMap(originalObj);
		Iterator<Object> itr = orderedMap.values().iterator();
		
		assertEquals("val3", itr.next());
		assertEquals("val4", itr.next());
		assertEquals("val2", itr.next());
		assertEquals("val5", itr.next());
		assertEquals("val1", itr.next());
		assertEquals("val6", itr.next());
		assertFalse(itr.hasNext());
	}
	
	
	@Test
	public void testLackingOrderRule() {
		originalObj.put("newAttr", "val7");
		orderedKeys.set(2, "newAttr");
		orderedKeys.set(4, "");
		Map<String, Object> orderedMap = new AttributesSort(orderedKeys).transformMap(originalObj);
		Iterator<Object> itr = orderedMap.values().iterator();
		
		assertEquals("val3", itr.next());
		assertEquals("val4", itr.next());
		assertEquals("val7", itr.next());
		assertEquals("val5", itr.next());
		assertEquals("val6", itr.next());
		assertNotNull(itr.next());
		assertNotNull(itr.next());
		assertFalse(itr.hasNext());
	}

}
