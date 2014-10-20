package FIIT.VI.YAGO.parser;

import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.SimpleFSDirectory;

public class YagoIndex {

	private YagoIndex() {
	}
	
	
	public static IndexWriter createIndex(SimpleFSDirectory dir,IndexWriterConfig config) throws IOException{
		IndexWriter w= new IndexWriter(dir, config);
		return w;	
	}
	
	
	
	
}
