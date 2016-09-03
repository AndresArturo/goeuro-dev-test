
package transformations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * FilterAndSort responsibility is to Filter and then Sort a Map
 * based on a given criteria for its attributes.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class FilterAndSort implements Transformation {
	
	private StaticSort sorter;
	private SimpleFilter filter;
	
	
	public FilterAndSort(List<String> attributes) {
		sorter = new StaticSort(attributes);
		filter = new SimpleFilter(attributes);
	}
	

	public FilterAndSort(String... attributes) {
		this(Arrays.asList(attributes));
	}
	

	/**
	 * Filters and orders a Map.
	 * Delegates the individual tasks of Filtering and Sorting.
	 * @see transformations.SimpleFilter#transform(java.util.Map)
	 * @see transformations.StaticSort#transform(java.util.Map)
	 */
	@Override
	public Map<String, Object> transform(Map<String, Object> originalObj) {
		return sorter.transform(filter.transform(originalObj));
	}

}
