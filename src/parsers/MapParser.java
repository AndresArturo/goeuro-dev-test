package parsers;

import java.util.List;
import java.util.Map;

/**
 * Provides a common interface to parsers that transform Maps 
 * representations of data into formatted String representations.
 * @author Andres Arturo Sanchez Dorantes
 */
public interface MapParser {

	/**
	 * Parses flat Maps to a specific String representation of it.
	 * @param maps The list of Maps to parse.
	 * @return A String parsed with certain format.
	 */
	public String parseMaps(List<Map<String,Object>> maps);

}