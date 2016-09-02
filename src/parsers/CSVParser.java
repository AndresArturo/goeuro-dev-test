package parsers;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * CSVParser is the class responsible for parsing single flat Map
 * representation of objects into a CSV formatted String.
 * A CSVParser object provides the algorithms to parse a 
 * Map into a String of the form:
 * <p>
 * "value1,value2,value3"
 * <p>
 * According to the order of appearance of the values in the Map.
 * 
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class CSVParser {
	
	/**
	 * The delimiter to be used between values in the String.
	 */
	private String delimiter;
	
	public CSVParser(String delimiter) {
		this.delimiter = delimiter;
	}
	
	
	/**
	 * Parses a flat Map to a String.
	 * In the specific case of parsing to a CSV string it is important
	 * to consider the order of the values in the Map.
	 * <p>
	 * {@link #delimiter} is used to separate values.
	 * @param mapObject The ordered Map to parse.
	 * @return A String of the form "value1,value2,value3" 
	 */
	public String parse(LinkedHashMap<String,Object> mapObject) {
		return mapObject.values().stream()
				.map(obj -> obj+"")
				.collect(Collectors.joining(delimiter));
	}

}
