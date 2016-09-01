package parsers;

import java.text.ParseException;
import java.util.Map;

/**
 * Provides a common interface to parsers that transform String 
 * representations of data into flat Maps representing the different
 * nested levels of objects by {@link #DELIMITER}-separated key
 * hierarchies.
 * <p>
 * For example: 
 * If we had a JSON of the form {"attr1":"val1","attr2":{"attr1":val2,"attr2":"val3"}}
 * The expected Map should be ["attr1":"val1","attr2.attr1":val2,"attr2.attr2":"val3"]
 * <p>
 * @author Andres Arturo Sanchez Dorantes
 */
public interface Parser {

	/**
	 * A constant value representing the delimiter of nesting levels
	 * in the keys of the Maps.
	 * Should not be modified after configuration.
	 */
	public static String DELIMITER = ".";

	/**
	 * Parses a raw string into the final Map objects and stores them.
	 * The data to parse can either represent a single object or an
	 * array of them, in which case it will parse every one into a
	 * new Map.
	 * <p>
	 * Adds the new generated Maps to the list of parsed objects.
	 * @param stringInfo A String representing a JSON object.
	 * @throws ParseException If the String is not a valid JSON object.
	 */
	public void parse(String stringInfo) throws ParseException;

	/**
	 * Retrieves the next Map-like object parsed.
	 * First object parsed first object returned.
	 * Each object is retrieved only ones after invocation of this method.
	 * @return The Map representation of a parsed object.
	 */
	public Map<String, Object> nextMap();

	/**
	 * Retrieves the number of parsed objects still in list.
	 * @return The number of parsed objects still available.
	 */
	public int mapsCount();

}