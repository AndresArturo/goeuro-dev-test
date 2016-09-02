package io;

import java.io.IOException;

/**
 * Provides a common interface to progressively query resources 
 * and services for information.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public interface Reader {

	/**
	 * Queries a specific resource or service and retrieves the response.
	 * The response should be an encoded String representing the data.
	 * <p>
	 * The Reader is expected to internally maintain track of the
	 * information that has been read and what is still left.
	 * @return An encoded String representing the information queried.
	 * @throws IOException If the query to the resource or service fails
	 *         or the answer is corrupted.
	 */
	public String read() throws IOException;
	
	
	/**
	 * Indicates whether there is more data to read from the resource.
	 * This method can only be considered accurate after {@link read()}
	 * has been called for the first time.
	 * <p>
	 * It should be safe and non-redundant to call {@link read()} 
	 * whenever this method returns true. 
	 * @return Whether there is still more data to read.
	 */
	public boolean isDataLeft();

}