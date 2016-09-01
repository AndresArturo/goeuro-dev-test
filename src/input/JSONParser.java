package input;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONParser is the class responsible for parsing string 
 * representations of JSON objects.
 * A JSONParser object provides the algorithms to parse a raw
 * string-represented JSON object into a flat Map<String,Object>
 * representing object hierarchies by 'delimiter'-separated keys.
 * <p>
 * Each JSONParser object manages a list of the Maps generated
 * and provides access to them.
 * 
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class JSONParser {
	
	private LinkedList<Map<String,Object>> objMapped; //Objects mapped
	private String delimiter;
	
	public JSONParser() {
		objMapped = new LinkedList<Map<String,Object>>();
		delimiter = ".";
	}
	
	public JSONParser(String hierarchyDelimiter) {
		this();
		delimiter = hierarchyDelimiter;
	}

	/**
	 * Parses a raw string into the final Map objects and stores them.
	 * The JSON string can either be a single object or an array of
	 * them, in which case it will parse everyone into its own.
	 * <p>
	 * Adds the new generated Maps to the list of parsed objects.
	 * @param rawJSON A String representing a JSON object.
	 * @throws ParseException If the String is not a valid JSON object.
	 */
	public void parseString(String rawJSON) throws ParseException {
		JSONArray jArray;
		
		if(!rawJSON.startsWith("["))
			rawJSON = "[" + rawJSON + "]";
		
		try {
			jArray = new JSONArray(rawJSON);
		} catch (JSONException e) {
			throw new ParseException("Error parsing the raw JSON string", 0);
		}
		
		jArray.forEach(jObj -> objMapped.add(parseSingleObject(jObj)));
		
//		objMapped = StreamSupport.stream(jArray.spliterator(), false)
//				.map(jObj -> parse(jObj))
//				.collect(Collectors.toCollection(LinkedList::new));
		
	}
	
	/**
	 * Parses a single JSON object or array into a flat Map.
	 * @param jObj The JSONObject or JSONArray to parse
	 * @return The Map representation of the JSON object or an empty Map
	 * 		   if the object passed is another type.
	 * @see Map
	 * @see JSONObject
	 * @see JSONArray
	 */
	public Map<String,Object> parseSingleObject(Object jObj) {
		Map<String,Object> map = new HashMap<>();
		
		if(jObj instanceof JSONObject)
			parse(map, "", (JSONObject) jObj);
		else
			parse(map, "", (JSONArray) jObj);
		
		return map;
	}
	

	/**
	 * Recursive parsing method for an Object-like hierarchy.
	 * @param map The Map where to store the parsed object.
	 * @param baseName The key's name hierarchy accumulated from previous calls. 
	 * @param toParse The object to parse.
	 * @see #parse(Map,String,JSONArray)
	 */
	public void parse(Map<String,Object> map, String baseName, JSONObject toParse) {
		toParse.keys().forEachRemaining(key -> {
			Object value = parseJSONVal(toParse.get(key));
			
			if(value instanceof JSONObject)
				parse(map, baseName+key+delimiter, (JSONObject) value);
			else if(value instanceof JSONArray) 
				parse(map, baseName+key+delimiter, (JSONArray) value);
			else
				map.put(baseName+key, value);
		});
	}
	
	
	/**
	 * Recursive parsing method for an Array-like hierarchy.
	 * @param map The Map where to store the parsed object.
	 * @param baseName The key's name hierarchy accumulated from previous calls. 
	 * @param toParse The object to parse.
	 * @see #parse(Map,String,JSONObject)
	 */
	public void parse(Map<String,Object> map, String baseName, JSONArray toParse) {
		Object jObj;
		
		for(int objI=0; objI < toParse.length(); objI++) 
		{
			jObj = parseJSONVal(toParse.get(objI));
			
			if(jObj instanceof JSONObject)
				parse(map, baseName+objI+delimiter, (JSONObject) jObj);
			else if(jObj instanceof JSONArray)
				parse(map, baseName+objI+delimiter, (JSONArray) jObj);
			else
				map.put(baseName+objI, jObj);
		}
	}
	
	
	/**
	 * Parses a single JSON value in accordance to Java types.
	 * @param val The JSON value to parse
	 * @return A precise representation of the JSON value in a Java Type.
	 */
	public Object parseJSONVal(Object val) {
		if(!(val instanceof JSONObject) && !(val instanceof JSONArray))
		{
			if(JSONObject.NULL.equals(val))
				val = null;
		}
		
		return val;
	}
	

	public Map<String,Object> nextMap() {
		return objMapped.removeFirst();
	}

	public Object mapsCount() {
		return objMapped.size();
	}

}
