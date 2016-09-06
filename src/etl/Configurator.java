
package etl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import io.*;
import parsers.*;
import transformations.*;

/**
 * Configurator is responsible for configurating the application and starting it.
 * Reads from a configuration file and sets default configuration values when not found.
 * @author Andres Arturo Sanchez Dorantes
 */
public class Configurator {

	
	public static void main(String[] args) {
		
		ETL etl;    // Extract-Transform-Load 
		
		Extractor extractor;
		Reader reader;           // Objects needed for the extraction process
		StringParser inParser;
		
		Transformation transformation; // Object needed for the transformation process
		
		Loader loader;
		Writer writer = null;           // Objects needed for the loading process
		MapParser outParser;
		
		
		if(args.length == 0) {
			System.err.println("No defined city to query");
			System.exit(1);
		}
			
		
		
		Properties props = new Properties();
		FileInputStream in;
		
		try {
			in = new FileInputStream("dev-test.conf");
			props.load(in);
			in.close();
		} catch(IOException ex) {}
		
		
		reader = new HttpReader(props.getProperty("API_URL", "http://api.goeuro.com/api/v2/position/suggest/en/") + args[0]);
		inParser = new JSONStringParser();
		StringParser.DELIMITER.setLength(0);
		StringParser.DELIMITER.append(props.getProperty("attributes_delimiter", ".")); 
		extractor = new Extractor(reader, inParser, 
								  Integer.parseInt(props.getProperty("reconnection_attempts", "3")), 
								  Long.parseLong(props.getProperty("reconnection_delay", "1")) * 1000);
		
		transformation = new AttributesFilterAndSort(props.getProperty("attributes_wanted", 
				"_id;name;type;geo_position.latitude;geo_position.longitude").split(";"));
		

		
		outParser = new CSVMapParser(props.getProperty("csv_delimiter", ","));
		try {
			writer = new LocalFileWriter(props.getProperty("csv_path", "goeuro.csv"));
		} catch (FileNotFoundException e) {
			System.err.println("Not possible to write CSV file");
			System.exit(2);
		}
		loader = new Loader(writer, outParser);
		
		etl = new ETL(extractor, loader, transformation);
		new Thread(etl).start();
	}
	

}
