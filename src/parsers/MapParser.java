package parsers;

import java.util.List;
import java.util.Map;

/**
 * Provides a common interface for parsers that transform Maps 
 * representations of data into formatted String representations.
 * @author Andres Arturo Sanchez Dorantes
 */
public interface MapParser {
	
	/**
	 * Parses flat Maps adding headers or meta-data.
	 * This method is only called once at the beginning of the parsing 
	 * process so that representation-specific headers or meta-data 
	 * can be included.
	 * @param maps The list of Maps to parse.
	 * @return A String parsed containing headers or necessary meta-data.
	 */
	public String firstParsing(List<Map<String,Object>> maps);

	/**
	 * Parses flat Maps to a specific String representation of it.
	 * This method can be called multiple times during the parsing process.
	 * @param maps The list of Maps to parse.
	 * @return A String parsed with certain format.
	 */
	public String parseMaps(List<Map<String,Object>> maps);
	
	
	/**
	 * Generates the endings or specific closing String for the
	 * representation being parsed.
	 * This method is only called once at the end of the parsing process.
	 * @return The String to write at the end of the parsing process.
	 */
	public String getEndings();

}