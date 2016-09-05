package transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Provides a common interface to transform lists of Map objects.
 * <p>
 * Example of transformations can be Filtering, Ordering, Reducing, etc.
 * <p>
 * @author Andres Arturo Sanchez Dorantes
 */
public abstract class Transformation {
	
	
	/**
	 * Transforms one Map object.
	 * Used to transform each Map of the whole list individually.
	 * @param originalMap The Map to transform.
	 * @return The transformed Map.
	 */
	public abstract Map<String, Object> transformMap(Map<String, Object> originalMap);


	/**
	 * Creates a new list of Maps as a result of applying a transformation
	 * on the original objects one by one.
	 * @param originalMaps The original list of Maps to transform.
	 * @return The transformed list of Maps.
	 */
	public List<Map<String, Object>> transformMaps(List<Map<String, Object>> originalMaps) {
		return originalMaps.stream()
				.map(this::transformMap)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	

}