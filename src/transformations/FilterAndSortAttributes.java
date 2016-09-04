
package transformations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * FilterAndSortAttributes responsibility is to Filter and then Sort a Map
 * based on a given criteria for its attributes.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class FilterAndSortAttributes extends Transformation {
	
	private AttributesSort sorter;
	private AttributesFilter filter;
	
	
	public FilterAndSortAttributes(List<String> attributes) {
		sorter = new AttributesSort(attributes);
		filter = new AttributesFilter(attributes);
	}
	

	public FilterAndSortAttributes(String... attributes) {
		this(Arrays.asList(attributes));
	}
	

	/**
	 * Filters and orders a Map.
	 * Delegates the individual tasks of Filtering and Sorting.
	 * @see transformations.AttributesFilter#transformMap(java.util.Map)
	 * @see transformations.AttributesSort#transformMap(java.util.Map)
	 */
	@Override
	public Map<String, Object> transformMap(Map<String, Object> originalObj) {
		return sorter.transformMap(filter.transformMap(originalObj));
	}


}
