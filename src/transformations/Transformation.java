package transformations;

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
	 * Creates a new Map as a result of applying some transformation on
	 * an original one.
	 * @param originalObj The original Map containing all the attributes.
	 * @return The processed Map.
	 */
	public Map<String, Object> transform(Map<String, Object> originalObj);

}