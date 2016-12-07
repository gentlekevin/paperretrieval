package com.lucene.index.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;


import org.apache.lucene.store.FSDirectory;

import com.bjut.search.IndexConstant;

public class IndexFPAuthorPapers {

	
	/**  
     * 搜索  
     * @param query  
     * @throws Exception  
     */  
    private static void search(Query query) throws Exception {  
        Directory dire=FSDirectory.open( Paths.get(IndexConstant.fpAuthorsPapersIndexPath));  
        IndexReader ir=DirectoryReader.open(dire);  
        IndexSearcher is=new IndexSearcher(ir);  
        TopDocs td=is.search(query, 1000);  
        System.out.println("共为您查找到"+td.totalHits+"条结果");  
        ScoreDoc[] sds =td.scoreDocs;  
        for (ScoreDoc sd : sds) {   
            Document d = is.doc(sd.doc);   
            System.out.println(d.get("id") + ":"+d.get("author")+"，score:"+sd.score);   
        }  
    }  
      
      
    public static void main(String[] args) throws Exception, Exception {  
        Analyzer analyzer=new WhitespaceAnalyzer();  
        
        QueryParser parser = new QueryParser("author", analyzer);   
        Query query = parser.parse("高允翔 肖创柏"); 
        Directory dire=FSDirectory.open( Paths.get(IndexConstant.fpAuthorsPapersIndexPath));  
        IndexReader ir=DirectoryReader.open(dire);  
        IndexSearcher is=new IndexSearcher(ir);  
        TopDocs td=is.search(query, 1000);  
        System.out.println("共为您查找到"+td.totalHits+"条结果");  
        ScoreDoc[] sds =td.scoreDocs;  
        for (ScoreDoc sd : sds) {   
            Document d = is.doc(sd.doc);   
            
            System.out.println(d.get("id") + ":"+d.get("author")+"，score:"+sd.score);   
        }  
        
    }  
}
