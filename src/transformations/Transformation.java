package transformations;

import java.util.List;
import java.util.Map;

/**
 * Provides a common interface to transform Map objects.
 * <p>
 * Example of transformations can be Filtering, Ordering, Reducing, etc.
 * <p>
 * @author Andres Arturo Sanchez Dorantes
 */
public interface Transformation {
	
	/**
	 * Creates a new list of Maps as a result of applying a transformation
	 * on the original objects.
	 * @param originalMaps The original Map containing all the attributes.
	 * @return The processed list of Maps.
	 */
	public List<Map<String, Object>> transform(List<Map<String, Object>> originalMaps);

}