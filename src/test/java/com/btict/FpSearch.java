package com.btict;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

public class FpSearch {

	 /**
	  * 
	  * @param { author1,author2.....}
	  * @return {{id1:j},{id2:i},{}....} 
	  * @throws IOException
	  */
	public static Map<Long,Long> getRelationPaperIds(String  authors) throws IOException {
		String fpIndex = "F:/index/fpindex";

		String fielduthor = "author";
		String queries = null;
		Map<Long,Long> paperIdCounts = new HashMap<Long,Long>();
		
		IndexReader fpReader = DirectoryReader.open(FSDirectory.open(Paths.get(fpIndex)));
		IndexSearcher fpsearcher = new IndexSearcher(fpReader);
		Analyzer analyzer = new SmartChineseAnalyzer();
		QueryParser parser = new QueryParser(fielduthor, analyzer);
		
		 
		 
		 
		 
		 
		 
		 return paperIdCounts;    
		

	}

}
