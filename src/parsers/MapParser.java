package parsers;

import java.util.Map;

public interface MapParser {

	/**
	 * Parses a flat Map to a specific String representation of it.
	 * @param map The Map representation of an object to parse.
	 * @return A String parsed to a format.
	 */
	public String parse(Map<String,Object> map);

}