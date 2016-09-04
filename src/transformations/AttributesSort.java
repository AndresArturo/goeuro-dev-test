
package transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AttributesSort is the class responsible for rearranging the
 * attributes of a Map given a static predefined order of them.
 * Represents a sort of attributes on a per-element basis, not
 * a sort of the list of Maps.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class AttributesSort extends Transformation {
	
	/**
	 * The list of attributes to preserve.
	 */
	private ArrayList<String> orderedAttr;
	
	
	public AttributesSort(List<String> attributes) {
		this.orderedAttr = new ArrayList<>(attributes);
	}
	
	
	public AttributesSort(String... attributes) {
		this(Arrays.asList(attributes));
	}

	
	/**
	 * Creates a new ordered Map.
	 * The new Map contains all the attributes of the original Map whose
	 * keys match the given order plus the rest of them unordered.
	 * @param originalMap The original Map to order..
	 * @return The ordered Map.
	 */
	@Override
	public Map<String, Object> transformMap(Map<String, Object> originalMap) {
		LinkedHashMap<String, Object> orderedObj = new LinkedHashMap<>();
		
		for(String attr : orderedAttr)
			if(originalMap.containsKey(attr))
				orderedObj.put(attr, originalMap.get(attr));
		
		orderedObj.putAll(originalMap);
		
			
		return orderedObj;
	}

}
