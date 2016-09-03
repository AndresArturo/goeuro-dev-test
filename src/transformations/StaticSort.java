
package transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * StaticSort is the class responsible for rearranging the
 * attributes of a Map given a static predefined order of them.
 * A StaticSort object sorts the Map based on a statically
 * provided order for its keys.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class StaticSort implements Transformation {
	
	/**
	 * The list of attributes to preserve.
	 */
	private ArrayList<String> orderedAttr;
	
	
	public StaticSort(List<String> attributes) {
		this.orderedAttr = new ArrayList<>(attributes);
	}
	
	
	public StaticSort(String... attributes) {
		this(Arrays.asList(attributes));
	}

	
	/**
	 * Creates a new ordered Map.
	 * The new Map contains all the attributes of the original Map whose
	 * keys match the given order plus the rest of them unordered.
	 * @param originalObj The original Map to order..
	 * @return The ordered Map.
	 */
	@Override
	public Map<String, Object> transform(Map<String, Object> originalObj) {
		LinkedHashMap<String, Object> orderedObj = new LinkedHashMap<>();
		
		for(String attr : orderedAttr)
			if(originalObj.containsKey(attr))
				orderedObj.put(attr, originalObj.get(attr));
		
		orderedObj.putAll(originalObj);
		
			
		return orderedObj;
	}

}
