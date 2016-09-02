package io;

import java.io.IOException;

/**
 * Provides a common interface to progressively store 
 * information in services or resources.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public interface Writer {

	
	/**
	 * Writes data to a specific resource or service.
	 * @param write The encoded data to write to the service.
	 * @throws IOException If the resource can't be reached, the 
	 *         connection is lost or the data is otherwise corrupted.
	 */
	public void write(String write) throws IOException;

	
	/**
	 * Informs the writer there is no more information to write.
	 * This method is intended to free the resources being used
	 * by the writer after all the data has been written.
	 */
	public void close();

}