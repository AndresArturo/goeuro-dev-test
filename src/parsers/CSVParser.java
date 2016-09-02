package parsers;

import java.util.HashMap;
import java.util.stream.Collectors;

public class CSVParser {
	
	private String delimiter;
	
	public CSVParser(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public String parse(HashMap<String,Object> mapObject) {
		return mapObject.values().stream()
				.map(obj -> obj+"")
				.collect(Collectors.joining(delimiter));
	}

}
