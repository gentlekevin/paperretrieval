package com.bjut.service.search;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Clock;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.bjut.entity.CNKI;
import com.bjut.entity.IEEE;
import com.bjut.entity.Paper;
import com.bjut.entity.Springer;
import com.bjut.repository.CNKIDao;
import com.bjut.repository.IEEEDao;
import com.bjut.repository.PaperDao;
import com.bjut.repository.SpringerDao;
import com.bjut.service.SpecificationFindUtil;
import com.bjut.search.IndexConstant;
import com.bjut.search.field.SingleAuthorField;
import com.bjut.search.field.SingleDucument;
import com.bjut.search.field.SingleIdField;
import com.bjut.search.field.SingleKeywordField;
import com.bjut.search.field.SingleSourceField;
import com.bjut.search.field.SingleTitleField;

/**
 * 搜索相关
 * @author yangkaiwen
 * 
 */

@Component
public class SearchService {

	private static Logger logger = LoggerFactory.getLogger(SearchService.class);
	private Clock clock = Clock.DEFAULT;
     @Autowired
	private PaperDao paperDao;
	
    public Map findPaperBypage(String querystring,String filed,String []databaseIndex,int start,int end) throws ParseException{
    	
    	    Analyzer PaperAnalyzer = new IKAnalyzer();
    	    //IndexReader paperreader;
    	    IndexSearcher searcher = null;
    	    IndexReader []	readers = new IndexReader[databaseIndex.length];
    	    IndexSearcher[] searchers = new IndexSearcher[databaseIndex.length];
    	    MultiReader multiReader = null;
    	    QueryParser parser = null;
    	    Query query = null;
    	    ScoreDoc[] hits=null;
    	    int numTotalHits = 0;
    	    List<Paper> papers = new ArrayList<Paper>();
    	    Map map = new HashMap();
    	   
    	   		try {   	   			
    	   			for(int i=0;i<databaseIndex.length;i++){
    	   			//注册所有的reader
    	   				readers[i]=DirectoryReader.open(FSDirectory.open(Paths.get(databaseIndex[i])));
    	   			}
    	   		multiReader = new MultiReader(readers);
    	   		searcher = new IndexSearcher(multiReader);
	    	    parser = new QueryParser(filed, PaperAnalyzer); 
	    	    query = parser.parse(querystring);
	    	    logger.info("Searching for:{} " , query.toString(filed));
	    	        	 
	    	    TopDocs results = searcher.search(query,  end);
	    	    hits = results.scoreDocs;
	    	    numTotalHits = results.totalHits;
	    	    logger.info("{} total matching documents",numTotalHits);
	    	    map.put("total", numTotalHits);     
	    	    
	    	    end  = Math.min(numTotalHits, end);
	    	    
	    	     for (int i = start; i < end; i++) {   	 
	    	     
	    	       Document doc = searcher.doc(hits[i].doc);
	    	       if(doc.get("id")!=null&&!"".equals(doc.get("id"))){
	    	    	Paper paper= paperDao.findOne(Long.parseLong(doc.get("id")));	
	    	    	papers.add(paper);
	    	    	  
	    	    }	      
	    	      map.put("papers", papers);  
	    	      }        	    
	    	    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
    	       
    	
    } 
	

}
