package processor;

import java.util.Map;

/**
 * Provides a common interface to processors of Map objects.
 * <p>
 * Example of processes can be Filtering, Ordering, Reducing, etc.
 * <p>
 * @author Andres Arturo Sanchez Dorantes
 */
public interface MapProcessor {

	/**
	 * Creates a new Map as a result of applying some operations on
	 * an original one.
	 * @param originalObj The original Map containing all the attributes.
	 * @return The processed Map.
	 */
	public Map<String, Object> process(Map<String, Object> originalObj);

}