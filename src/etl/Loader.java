
package etl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.Writer;
import parsers.MapParser;

/**
 * Loader is the class responsible for loading transformed data
 * into a given source.
 * Uses for that purpose a MapParser to make the internal data 
 * understandable for an external resource and a Writer to
 * to write it to it.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class Loader {
	
	
	private Writer writer;
	private MapParser parser;
	private Boolean firstLoad;
	
	
	public Loader(Writer writer, MapParser parser) {
		super();
		this.writer = writer;
		this.parser = parser;
		this.firstLoad = new Boolean(true);
	}
	
	
	/**
	 * Loads data into the resource.
	 * Parses the internal data and writes it to the resource.
	 * <p>
	 * Handles the logic whether it is the first time loading to a resource
	 * or a middle operation to ensure the data is loaded logically. 
	 * <p>
	 * Synchronizes by intrinsic lock the writing process and if it fails
	 * then logs the non-saved data.
	 */
	public void load(List<Map<String,Object>> maps) {
		boolean tmpFirstLoad;
		String parsedStr;
		
		synchronized(firstLoad) {
			tmpFirstLoad = firstLoad;
			firstLoad = false;
		}
		
		//Frees the threads to parse without locking
		parsedStr = tmpFirstLoad ? parser.firstParsing(maps) : parser.parseMaps(maps);
		
		//Only one thread writes the same resource at a time
		synchronized (writer) {
			try {
				writer.write(parsedStr);
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.err.println("Lost data: " + parsedStr);
			}
		}
	}
	
	
	/**
	 * Loads last data and closes connections.
	 * Used to make sure the data loaded to a resource is well finished.
	 * <p>
	 * Should only be called once at the end of the loading process.
	 */
	public void finish() {
		try {
			writer.write(parser.getEndings());
		} catch (IOException e) {}
		writer.close();
	}

}
