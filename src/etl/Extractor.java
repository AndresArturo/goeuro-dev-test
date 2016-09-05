
package etl;

import io.Reader;
import parsers.StringParser;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Extractor is the class responsible for extracting data from a given
 * source to make it available for internal processing.
 * It uses for that purpose a Reader to read information from a source
 * and a StringParser to make it understandable by the program.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class Extractor {
	
	private Reader reader;
	private StringParser parser;
	private int maxConnectionAttempts;
	private int connectionAttempts;
	private long reconnectionDelay;
	private boolean badConfig;
	
	
	
	public Extractor(Reader reader, StringParser parser, int maxConnectionAttempts, long reconnectionDelay) {
		super();
		this.reader = reader;
		this.parser = parser;
		this.maxConnectionAttempts = maxConnectionAttempts;
		this.reconnectionDelay = reconnectionDelay;
		this.connectionAttempts = 0;
		this.badConfig = false;
	}
	


	/**
	 * Extracts data from the source.
	 * Reads data from the source and parses it.
	 * <p>
	 * Implements a mechanism of reconnection to attempt to recover from any exceptions
	 * resulting from the extracting process.
	 * @return The list of extracted Maps or null in case of a fatal exception.
	 */
	public List<Map<String,Object>> extract()  {
		String toParse = "";
		
		try {
			toParse = reader.read(); // Reads from the connection
			connectionAttempts = 0; // Successful connection, restarts the counting of failed connections
			return parser.parseString(toParse);
			
		} catch (IOException ex) { // Try to recover
			System.err.println(ex.getMessage() + " at " + reader.getClass()); // Logs the exception
			return retry(); // Attempts to extract again
			
		} catch (ParseException e) { // Parser incompatible with data read
			System.err.println(e.getMessage()); // Logs the exception
			badConfig = true;
			return null; // Can't recover configuration errors
		}
		
	}
	
	
	
	/**
	 * Attempts to reconnect after a failed reading.
	 * @return The list of extracted Maps or null if the recover is unsuccessful.
	 */
	private List<Map<String,Object>> retry() {
		connectionAttempts++; //Increases the number of failed connections
		
		if(this.canExtract()) {
			try {
				Thread.sleep(reconnectionDelay); //Waits to retry connection
			} catch (InterruptedException e) {}
			return this.extract();
		}
		else {
			System.err.println("Cannot recover failed connection");
			return null;
		}
	}
	
	
	
	/**
	 * Indicates whether there is more data that should be attempted to extract.
	 * Used for pagination and resilience purposes.
	 * @return Whether there is more data available.
	 */
	public boolean canExtract() {
		return  !badConfig 
				&& connectionAttempts < maxConnectionAttempts 
				&& reader.isDataLeft();
	}
	

}
