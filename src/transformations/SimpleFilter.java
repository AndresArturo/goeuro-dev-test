package transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SimpleFilter is the class responsible for cleaning unwanted 
 * attributes off of a Map.
 * A SimpleFilter object provides the algorithm to generate new
 * Maps containing only desired attributes.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class SimpleFilter implements Transformation {
	
	/**
	 * The list of attributes to preserve.
	 */
	private ArrayList<String> wantedAttr;
	
	
	public SimpleFilter(List<String> attributes) {
		this.wantedAttr = new ArrayList<>(attributes);
	}
	

	public SimpleFilter(String... attributes) {
		this(Arrays.asList(attributes));
	}
	

	/**
	 * Creates a new Map containing only the desired attributes.
	 * The new Map contains the attributes resulting from the 
	 * intersection of the sets of the original Map's keys and the 
	 * desired keys.
	 * @param originalObj The original Map containing all the attributes.
	 * @return The filtered Map.
	 */
	@Override
	public Map<String, Object> transform(Map<String, Object> originalObj) {
		Map<String, Object> filteredObj = new HashMap<>();
		
		for(String attr : wantedAttr)
			if(originalObj.containsKey(attr))
				filteredObj.put(attr, originalObj.get(attr));
		
		return filteredObj;
	}

	

}
