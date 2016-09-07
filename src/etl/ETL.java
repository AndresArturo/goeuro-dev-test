
package etl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import transformations.Transformation;

/**
 * ETL is responsible to handle the ETL process.
 * Administers the extraction, transformation and load process in
 * a multi-threaded fashion. 
 * @author Andres Arturo Sanchez Dorantes
 *
 */
public class ETL implements Runnable {
	
	private Extractor extractor;
	private Loader loader;
	private Transformation transformation;
	

	public ETL(Extractor extractor, Loader loader, Transformation transformation) {
		super();
		this.extractor = extractor;
		this.loader = loader;
		this.transformation = transformation;
	}


	@Override
	public void run()
	{	
		LinkedList<CompletableFuture<Void>> futureETLs = new LinkedList<>();
		
		while(extractor.canExtract()) //Implements pagination
		{ 
			List<Map<String,Object>> extractedMaps = extractor.extract();
			
			if(extractedMaps != null)
				futureETLs.add(CompletableFuture  //Asynchronously transforms and loads the data
							   .supplyAsync(()->transformation.transformMaps(extractedMaps))
							   .thenAccept(loader::load));
		}
		
		
		for(CompletableFuture<Void> futureETL : futureETLs)
			try {
				futureETL.get();
			} catch (InterruptedException | ExecutionException e) {
				System.err.println(e.getMessage());
			}
		
		
		loader.finish();
	}
	
	
}
