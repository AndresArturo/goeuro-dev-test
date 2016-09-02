package parsers;

import java.util.Map;

/**
 * Provides a common interface to parsers that transform Maps 
 * representations of data into formatted String representations.
 * @author Andres Arturo Sanchez Dorantes
 */
public interface MapParser {

	/**
	 * Parses a flat Map to a specific String representation of it.
	 * @param map The Map representation of an object to parse.
	 * @return A String parsed with certain format.
	 */
	public String parse(Map<String,Object> map);

}