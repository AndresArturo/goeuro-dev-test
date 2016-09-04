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
	 * information that has been read and what is still left in order
	 * to allow for pagination.
	 * @return An encoded String representing the information queried.
	 * @throws IOException If the query to the resource or service fails
	 *         or the answer is corrupted.
	 */
	public String read() throws IOException;
	
	
	/**
	 * Indicates whether there is more data to read from the resource.
	 * Specifies only whether logically speaking there should be more
	 * data to read but does nothing to prevent from errors. 
	 * <p>
	 * It should be non-redundant to call {@link read()} whenever this
	 * method returns true. Can be used as means of pagination. 
	 * @return Whether there is still more data to read.
	 */
	public boolean isDataLeft();

}