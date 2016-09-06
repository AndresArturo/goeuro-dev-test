/**
 * Test class created following the TDD process.
 */
package etl;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.Writer;
import parsers.CSVMapParser;
import parsers.MapParser;

/**
 * Tests the Loader class.
 * The class responsibility is to load information into a given resource.
 * <p>
 * The functionality of Loader should be:
 * <ul>
 * <li>Load data through a process of parsing and writing.
 * <li>Provide a thread-safe mechanism to make paginated loads
 * 		(start, multiple internal writings, finish). 
 * </ul>
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class LoaderTest {

	private MapParser parser;
	private Writer writer;
	private ArrayList<String> resourceToLoad;
	
	
	@Before
	public void setUp() throws Exception {
		parser = new CSVMapParser(",");
		resourceToLoad = new ArrayList<>();
		
		writer = new Writer() {
			@Override
			public void write(String write) throws IOException {
				resourceToLoad.add(write);
			}
			
			@Override
			public void close() {}
		};
	}
	
	@After
	public void tearDown() throws Exception {
		resourceToLoad.clear();
	}
	
	
	@Test
	public void testLoad() {
		Loader loader = new Loader(writer, parser);
		Map<String,Object> map = new HashMap<>();
		
		map.put("name", "map1");
		map.put("type", "HashMap");
		
		loader.load(Arrays.asList(map,map));
		loader.load(Arrays.asList(map,map));
		loader.finish();
		
		assertEquals(3, resourceToLoad.size()); //First writing, second writing and finalization
		
		assertEquals("name,type"+System.getProperty("line.separator")  //First time writing prints titles
					+"map1,HashMap"+System.getProperty("line.separator")
					+"map1,HashMap"+System.getProperty("line.separator"), resourceToLoad.get(0));
		
		assertEquals("map1,HashMap"+System.getProperty("line.separator") //Second time writing does not print titles
					+"map1,HashMap"+System.getProperty("line.separator"), resourceToLoad.get(1));
	}
	

	@Test
	public void testMultiThreadedLoad() {
		Loader loader = new Loader(writer, parser);
		ArrayList<Thread> threads = new ArrayList<>();
		int noThreads = 5;
		
		for (int threadI = 0; threadI < noThreads; threadI++) {
			Map<String,Object> map = new HashMap<>();
			map.put("name", "map" + threadI);
			map.put("type", "HashMap");
			threads.add(new Thread(()->loader.load(Arrays.asList(map,map,map,map))) );
		}
		
		threads.forEach(Thread::start);
		threads.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {}
		});
		
		assertEquals(noThreads, resourceToLoad.size()); //All info loaded by all Threads
		
		int hasTitles = 0;
		for(String loaded : resourceToLoad)
			if(loaded.startsWith("name,type"))
				hasTitles++;
		assertEquals(1, hasTitles); //Titles written only once
	}

}
