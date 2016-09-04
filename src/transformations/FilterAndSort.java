
package transformations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	 * @see transformations.SimpleFilter#individualTransformation(java.util.Map)
	 * @see transformations.StaticSort#individualTransformation(java.util.Map)
	 */
	public Map<String, Object> individualTransformation(Map<String, Object> originalObj) {
		return sorter.attributesSorting(filter.individualTransformation(originalObj));
	}


	@Override
	public List<Map<String, Object>> transform(List<Map<String, Object>> originalMaps) {
		return originalMaps.stream()
				.map(this::individualTransformation)
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
