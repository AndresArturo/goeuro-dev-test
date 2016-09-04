package parsers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * CSVMapParser is the class responsible for parsing flat
 * Maps into a CSV formatted String.
 * A CSVMapParser object provides the algorithms to parse
 * a list of Maps into a String of the form:
 * <p>
 * "Map1.value1,Map1.value2" + <system specific new line> +
 * "Map2.value1,Map2.value2"
 * <p>
 * According to the order of appearance of the Maps and 
 * the values in each Map. Assuming ','  is given as the
 * delimiter.
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
	
	
	/** 
	 * Parses a single Map into a CSV line.
	 * In this specific case of parsing to a CSV-formated String it
	 * is important to consider the order of the values in the Map.
	 * {@link #delimiter} is used to separate values.
	 * @return A String representing a Map as a single line.
	 */
	public String parse(Map<String,Object> map) {
		return map.values().stream()
				.map(obj -> obj+"")
				.collect(Collectors.joining(delimiter));
	}


	/* (non-Javadoc)
	 * Appends each Map parsed as a new line to the string
	 * @see parsers.MapParser#parseMaps(java.util.List)
	 */
	@Override
	public String parseMaps(List<Map<String, Object>> maps) {
		return maps.stream()
				.map(this::parse)
				.collect(Collectors.joining(System.getProperty("line.separator")));
	}

	

}
