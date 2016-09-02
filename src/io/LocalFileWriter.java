package io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class LocalFileWriter implements Writer {
	
	PrintWriter writer;
	
	public LocalFileWriter(String path) throws FileNotFoundException {
		writer = new PrintWriter(path);
	}
	
	
	/* (non-Javadoc)
	 * @see io.Writer#write(java.lang.String)
	 */
	@Override
	public void write(String write) throws IOException {
		writer.print(write);
		
		if(writer.checkError())
			throw new IOException("Could not write to local file");
	}
	
	
	/* (non-Javadoc)
	 * @see io.Writer#close()
	 */
	@Override
	public void close() {
		writer.close();
	}

}
