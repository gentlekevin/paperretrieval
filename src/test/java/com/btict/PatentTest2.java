package com.btict;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import com.bjut.entity.AuthorsPapers;
import com.bjut.entity.Paper;

public class PatentTest2 {
	public static  String fieldTitle = "title";
	 public static String fpfield ="authors";
	 public static int hitsPerPage=10;
  public static void main(String[] args) throws Exception {
    
    System.out.println("*******Initial work is begin****************");
    //paperSearch 参数
    int hitsPerPage = 10;
    String paperIndex = "F:/index/papersindex";
  
    Analyzer PaperAnalyzer = new SmartChineseAnalyzer();
    IndexReader paperreader = DirectoryReader.open(FSDirectory.open(Paths.get(paperIndex)));
    IndexSearcher paperSearcher = new IndexSearcher(paperreader);
    QueryParser paperparser = new QueryParser(fieldTitle, PaperAnalyzer);    
   //fpSearch 参数
    String fpIndex  = "F:/index/fpindex";
    
    Analyzer fpAnalyzer = new SmartChineseAnalyzer();
    IndexReader fpReader = DirectoryReader.open(FSDirectory.open(Paths.get(fpIndex)));
    IndexSearcher fpSearcher = new IndexSearcher(fpReader);
    QueryParser fpParser = new QueryParser(fpfield, fpAnalyzer);    
    System.out.println("*******Initial work is ok!****************");
    
    BufferedReader in = null;
    in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    while (true) {
       // prompt the user
     System.out.println();
     System.out.println();
      System.out.println("Enter query and you will get paper which you want: ");
      String queryString= in.readLine();
      //检查输入串是否合法
      if (queryString == null || queryString.length() == -1) { break; }
      queryString = queryString.trim();
      if (queryString.length() == 0) { break;}
           
      List<Paper> papers = papersSearch(paperSearcher, paperparser,  queryString);
      
      Map <String,Paper> IDPapers = new HashMap<String,Paper>();
      
      for(Paper paper:papers){
    	  IDPapers.put(String.valueOf(paper.getId()),paper); 
    	  
     	 System.out.println("Id:"+paper.getId()+", title:"+paper.getTitle()+",   author:"+paper.getAuthor()+",   "
     	 		+ "keyword:"+paper.getKeyword());
      }
      
        while(true){
        	 System.out.println();
        	 System.out.println();
        	 System.out.println("Input the id  to get interesting papers  (q:quit)： ");
        	 String id= in.readLine();
        	//检查输入串是否合法
        	  if (id == null || id.length() == -1) { break; }
        	 id = id.trim();
             if (id.length() == 0) { break;}
             if("q".equals(id)||"Q".equals(id)){break;}
             if(!IDPapers.containsKey(id)){
            	 
            	 System.out.println("The papers above don't contain this id!");
              continue;
             }
             System.out.println("The recommanded papers are :");
             
             List<Paper> paperss = recommend(IDPapers.get(id).getAuthor().split(","),IDPapers.get(id).getKeyword().split(" "),
            		 fpSearcher, fpParser, hitsPerPage);
             for(Paper paper:paperss){
           	     if(Long.parseLong(id)==paper.getId())continue;
            	 System.out.println("Id:"+paper.getId()+", title:"+paper.getTitle()+",   author:"+paper.getAuthor()+",   "
            	 		+ "keyword:"+paper.getKeyword());
             }
        }  
    }
    paperreader.close();
    fpReader.close();
    
  }
  public static List<Paper> papersSearch( IndexSearcher searcher, QueryParser parser, 
		  String queryString) throws IOException, ParseException {
	  Query query = parser.parse(queryString);
      System.out.println("Searching for: " + query.toString(fieldTitle));
 
    TopDocs results = searcher.search(query,  hitsPerPage);
    ScoreDoc[] hits = results.scoreDocs;
    List<String> ids = new ArrayList<String>();

    int numTotalHits = results.totalHits;
    System.out.println(numTotalHits + " total matching documents");
    int start = 0;
     for (int i = start; i < hitsPerPage; i++) {   	 
        Document doc = searcher.doc(hits[i].doc);
       ids.add(doc.get("id"));            
      }    
    return  PaperDAO.findPaperByIds(ids);
   }
  
  
  
  
  
  public static List<Paper> recommend(String[] authors,String[] keywords, IndexSearcher searcher, QueryParser parser, 
		  int hitsPerPage) throws IOException, ParseException {
    //在fp表中得到所有相关的论文的id 并放近paperIdScore<key：,count:出现的次数>
	  Map<String,Float> paperIdScore = new HashMap<String, Float>();
	  float keywordWeight = 0.8f;
	  int sequence = 0;
	  for(String author:authors){
		  //获得每个作者的文章及打分
		  sequence++;
		  List<String> PaperIds = PaperDAO.findPaperIdByAuhtor(author);
			for(String paperId:PaperIds){
	    		if(paperIdScore.containsKey(paperId)){
	    			paperIdScore.put(paperId, paperIdScore.get(paperId)+getScoreByAuthorSequence(sequence));
	    		}else{
	    				    			
	    			paperIdScore.put(paperId, getScoreByAuthorSequence(sequence));
	    		}
	    	}
		  
	  }
	  for(String keyword:keywords){
		   //获得关键字对应的的论文
		    List<String> PaperIds = PaperDAO.findPaperIdByKeyword(keyword);
			for(String paperId:PaperIds){
	    		if(paperIdScore.containsKey(paperId)){
	    			paperIdScore.put(paperId, paperIdScore.get(paperId)+keywordWeight);
	    		}else{
	    				    			
	    			paperIdScore.put(paperId, keywordWeight);
	    		}
	    	}
		  
	  }	  
	  sequence = 0;
	  
	  for(String author:authors){
		    sequence++;//作者所在的序列
		    List<String> PaperIds = FpDAO.findPaperIdsByAuthor(author);
					for(String paperId:PaperIds){
			    		if(paperIdScore.containsKey(paperId)){
			    			paperIdScore.put(paperId, paperIdScore.get(paperId)+getScoreByFPAuthorSequence(sequence));
			    		}else{
			    				    			
			    			paperIdScore.put(paperId, getScoreByFPAuthorSequence(sequence));
			    		}
			    	}   
	  }
	  
	  List<Map.Entry<String,Float>> list = new ArrayList<Map.Entry<String,Float>>(paperIdScore.entrySet());
      Collections.sort(list,new Comparator<Map.Entry<String,Float>>() {
          //升序排序
          public int compare(Entry<String, Float> o1,
                  Entry<String, Float> o2) {
              return o2.getValue().compareTo(o1.getValue());
          }
          

      });
      int count = 0;
      List<String>recommendedPapersId = new ArrayList<String>();
      for(Map.Entry<String,Float> mapping:list){ 
    	  
    	  recommendedPapersId.add(mapping.getKey());
    	  count++;
    	  if(count>=11)break;
     }    
    
    return  PaperDAO.findPaperByIds(recommendedPapersId);
   }
  /**
   * 根据作者所在的序列，给定一分数
   * @param i author 的序列
   * @return 
   */
  public static float getScoreByFPAuthorSequence(int i){
	  
	  float score =0;
		switch(i){
		case 1:score=2f;break;
		case 2:score=1f;break;
		case 3:score=0.5f;break;
		default:score=0.2f ;break;
		}
	  
	return score;
	  
  }
public static float getScoreByAuthorSequence(int i){
	  
	  float score =0;
		switch(i){
		case 1:score=1;break;
		case 2:score=0.5f;break;
		case 3:score=0.2f;break;
			
		default:score=0.1f ;break;
		}
	  
	return score;
	  
  }
  
}
