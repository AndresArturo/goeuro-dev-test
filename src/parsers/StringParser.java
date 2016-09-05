package parsers;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Provides a common interface for parsers that transform String 
 * representations of data into flat Maps representing the different
 * nested levels of objects by {@link #DELIMITER}-separated key
 * hierarchies.
 * <p>
 * For example: 
 * Given a JSON of the form {"attr1":"val1","attr2":{"attr1":val2,"attr2":"val3"}}
 * The Map parsed should be ["attr1":"val1","attr2.attr1":val2,"attr2.attr2":"val3"]
 * <p>
 * @author Andres Arturo Sanchez Dorantes
 */
public interface StringParser {

	/**
	 * A constant value representing the delimiter of nesting levels
	 * in the keys of the Maps.
	 * Should not be modified after configuration.
	 */
	public static StringBuilder DELIMITER = new StringBuilder(".");

	/**
	 * Parses a raw string into the final Map objects.
	 * The data to parse can either represent a single object or an
	 * array of them, in which case it should parse each one into a
	 * new Map.
	 * <p>
	 * If there is no data to parse, i.e. a non-null empty String, the result
	 * of parsing should be an empty list.
	 * @param stringInfo A formatted String representing the data.
	 * @throws ParseException If the String is not valid for the format intended to parse.
	 * @return A List containing the resulting Maps.
	 */
	public List<Map<String,Object>> parseString(String stringData) throws ParseException;

}