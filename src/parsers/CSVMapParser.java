package parsers;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * CSVMapParser is the class responsible for parsing single flat
 * Map representation of objects into a CSV formatted String.
 * A CSVMapParser object provides the algorithms to parse a 
 * Map into a String of the form:
 * <p>
 * "value1,value2,value3"
 * <p>
 * According to the order of appearance of the values in the Map and
 * given ',' as the delimiter.
 * 
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class CSVMapParser implements MapParser {
	
	/**
	 * The delimiter to be used between values in the String.
	 */
	private String delimiter;
	
	public CSVMapParser(String delimiter) {
		this.delimiter = delimiter;
	}
	
	
	/* (non-Javadoc)
	 * In this specific case of parsing to a CSV-formated string it
	 * is important to consider the order of the values in the Map.
	 * {@link #delimiter} is used to separate values.
	 * @see parsers.MapParser#parse(java.util.LinkedHashMap)
	 */
	@Override
	public String parse(Map<String,Object> map) {
		return map.values().stream()
				.map(obj -> obj+"")
				.collect(Collectors.joining(delimiter));
	}

}
