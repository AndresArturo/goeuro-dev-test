package input;

import java.io.IOException;

/**
 * Provides a common interface to query resources 
 * and services for information.
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public interface Reader {

	/**
	 * Queries a specific resource or service and retrieves the response.
	 * The response should be an encoded String representing the data.
	 * @return A encoded String representing the information queried.
	 * @throws IOException If the query to the resource or service fails
	 *         or the answer is corrupted.
	 */
	public String read() throws IOException;

}