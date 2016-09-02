package io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class LocalFileWriter {
	
	PrintWriter writer;
	
	public LocalFileWriter(String path) throws FileNotFoundException {
		writer = new PrintWriter(path);
	}
	
	
	public void write(String write) {
		writer.print(write);
	}
	
	
	public void close() {
		writer.close();
	}

}
