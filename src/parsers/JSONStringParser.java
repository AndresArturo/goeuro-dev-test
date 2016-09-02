package parsers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONStringParser is the class responsible for parsing string 
 * representations of JSON objects.
 * A JSONParser object provides the algorithms to parse a raw
 * string-represented JSON object into a flat Map<String,Object>
 * representing object hierarchies by 'delimiter'-separated keys.
 * <p>
 * Each JSONStringParser object manages a list of the Maps generated
 * and provides access to them.
 * 
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class JSONStringParser implements StringParser {
	
	
	private LinkedList<Map<String,Object>> objMapped; //Objects mapped
	
	
	public JSONStringParser() {
		objMapped = new LinkedList<Map<String,Object>>();
	}

	
	/* (non-Javadoc)
	 * @see parsers.Parser#parseString(java.lang.String)
	 */
	@Override
	public void parseString(String rawJSON) throws ParseException {
		JSONArray jArray;
		
		if(!rawJSON.startsWith("["))
			rawJSON = "[" + rawJSON + "]";
		
		try {
			jArray = new JSONArray(rawJSON);
		} catch (JSONException e) {
			throw new ParseException("Error parsing the raw JSON string", 0);
		}
		
		jArray.forEach(jObj -> objMapped.add(parseSingleJSON(jObj)));
		
//		objMapped = StreamSupport.stream(jArray.spliterator(), false)
//				.map(jObj -> parse(jObj))
//				.collect(Collectors.toCollection(LinkedList::new));
		
	}
	
	
	/**
	 * Parses a single JSON object or array into a flat Map.
	 * @param jObj The JSONObject or JSONArray to parse.
	 * @return The Map representation of the JSON object or an empty Map
	 * 		   if the object passed is another type.
	 * @see Map
	 * @see JSONObject
	 * @see JSONArray
	 */
	public Map<String,Object> parseSingleJSON(Object jObj) {
		Map<String,Object> map = new HashMap<>();
		
		if(jObj instanceof JSONObject)
			parseJSON(map, "", (JSONObject) jObj);
		else
			parseJSON(map, "", (JSONArray) jObj);
		
		return map;
	}
	

	/**
	 * Recursive parsing method for an Object-like hierarchy.
	 * @param map The Map where to store the parsed object.
	 * @param baseName The key's name hierarchy accumulated from previous calls. 
	 * @param toParse The object to parse.
	 * @see #parseJSON(Map,String,JSONArray)
	 */
	private void parseJSON(Map<String,Object> map, String baseName, JSONObject toParse) {
		toParse.keys().forEachRemaining(key -> {
			Object value = parseJSONVal(toParse.get(key));
			
			if(value instanceof JSONObject)
				parseJSON(map, baseName+key+StringParser.DELIMITER, (JSONObject) value);
			else if(value instanceof JSONArray) 
				parseJSON(map, baseName+key+StringParser.DELIMITER, (JSONArray) value);
			else
				map.put(baseName+key, value);
		});
	}
	
	
	/**
	 * Recursive parsing method for an Array-like hierarchy.
	 * @param map The Map where to store the parsed object.
	 * @param baseName The key's name hierarchy accumulated from previous calls. 
	 * @param toParse The object to parse.
	 * @see #parseJSON(Map,String,JSONObject)
	 */
	private void parseJSON(Map<String,Object> map, String baseName, JSONArray toParse) {
		Object jObj;
		
		for(int objI=0; objI < toParse.length(); objI++) 
		{
			jObj = parseJSONVal(toParse.get(objI));
			
			if(jObj instanceof JSONObject)
				parseJSON(map, baseName+objI+StringParser.DELIMITER, (JSONObject) jObj);
			else if(jObj instanceof JSONArray)
				parseJSON(map, baseName+objI+StringParser.DELIMITER, (JSONArray) jObj);
			else
				map.put(baseName+objI, jObj);
		}
	}
	
	
	/**
	 * Parses a single JSON value in accordance to Java types.
	 * @param val The JSON value to parse.
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
	

	/* (non-Javadoc)
	 * @see parsers.Parser#nextMap()
	 */
	@Override
	public Map<String,Object> nextMap() {
		return objMapped.removeFirst();
	}

	/* (non-Javadoc)
	 * @see parsers.Parser#mapsCount()
	 */
	@Override
	public int mapsCount() {
		return objMapped.size();
	}

}
