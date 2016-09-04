package transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Provides a common interface to transform Map objects.
 * <p>
 * Example of transformations can be Filtering, Ordering, Reducing, etc.
 * <p>
 * @author Andres Arturo Sanchez Dorantes
 */
public abstract class Transformation {
	
	
	public abstract Map<String, Object> transformMap(Map<String, Object> originalMap);


	/**
	 * Creates a new list of Maps as a result of applying a transformation
	 * on the original objects.
	 * @param originalMaps The original Map containing all the attributes.
	 * @return The processed list of Maps.
	 */
	public List<Map<String, Object>> transformMaps(List<Map<String, Object>> originalMaps) {
		return originalMaps.stream()
				.map(this::transformMap)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	

}