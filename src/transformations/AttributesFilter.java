package transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AttributesFilter is the class responsible for cleaning unwanted 
 * attributes off of a Map.
 * An AttributesFilter object provides the algorithm to generate new
 * Maps containing ALL the desired attributes.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class AttributesFilter extends Transformation {
	
	/**
	 * The list of attributes to preserve.
	 */
	private ArrayList<String> wantedAttr;
	
	
	public AttributesFilter(List<String> attributes) {
		this.wantedAttr = new ArrayList<>(attributes);
	}
	

	public AttributesFilter(String... attributes) {
		this(Arrays.asList(attributes));
	}
	

	/**
	 * Creates a new Map containing ALL the desired attributes.
	 * The new Map contains exactly the attributes wanted, if the original
	 * map does not have them then they are created with a value of null.
	 * @param originalObj The original Map.
	 * @return The filtered Map.
	 */
	@Override
	public Map<String, Object> transformMap(Map<String, Object> originalObj) {
		Map<String, Object> filteredObj = new HashMap<>();
		
		for(String attr : wantedAttr)
				filteredObj.put(attr, originalObj.get(attr));
		
		return filteredObj;
	}
	

}
