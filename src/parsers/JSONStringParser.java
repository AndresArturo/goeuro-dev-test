package parsers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONStringParser is the class responsible for parsing String 
 * representations of JSON objects.
 * A JSONParser object provides the algorithms to parse a raw
 * String-represented list of JSON objects into a list of flat 
 * Map<String,Object> each of which represents object hierarchies 
 * by 'delimiter'-separated keys.
 * 
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class JSONStringParser implements StringParser {

	
	/* (non-Javadoc)
	 * @see Parser#parseString(java.lang.String)
	 */
	@Override
	public List<Map<String,Object>> parseString(String rawJSON) throws ParseException {
		LinkedList<Map<String,Object>> objMapped; //Objects mapped
		JSONArray jArray;
		JSONObject jObject;
		boolean isArray;
		
		objMapped = new LinkedList<>();
		
		try {
			jArray = new JSONArray(rawJSON);
			jArray.forEach(jObj -> objMapped.add(parseSingleJSON(jObj)));
			isArray = true;
		} catch (JSONException e) {
			isArray = false;
		}
		
		if(!isArray)
			try {
				jObject = new JSONObject(rawJSON);
				objMapped.add(parseSingleJSON(jObject));
			} catch (JSONException e) {
				throw new ParseException("Error parsing the raw JSON string", 0);
			}
			
		
		return objMapped;
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
		else if(jObj instanceof JSONArray)
			parseJSON(map, "", (JSONArray) jObj);
		else
			map.put(jObj.toString(), parseJSONVal(jObj));
		
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

}
