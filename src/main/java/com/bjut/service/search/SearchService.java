package com.bjut.service.search;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.ByteBlockPool.DirectAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Clock;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.bjut.entity.AuthorsPapers;
import com.bjut.entity.FpAuthorPaper;
import com.bjut.entity.HotPaper;
import com.bjut.entity.Paper;
import com.bjut.repository.AuthorPapersDao;
import com.bjut.repository.AuthorsPapersDao;
import com.bjut.repository.FpAuthorPaperDao;
import com.bjut.repository.HotPaperDao;
import com.bjut.repository.KeywordPaperDao;
import com.bjut.repository.PaperDao;
import com.bjut.search.IndexConstant;
import com.bjut.search.SearchUtil;
import com.bjut.service.SpecificationFindUtil;

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
     @Autowired
    private FpAuthorPaperDao fpAuthorPaperDao;
     @Autowired
    private KeywordPaperDao keywordPaperDao;
     @Autowired
    private AuthorPapersDao authorPapersDao; 
     @Autowired
     private AuthorsPapersDao authorsPapersDao;
    
    
    
	/**
	 * 
	 * @param querystring  要查询的字符串数组
	 * @param fields       要查询的字符串对应的数组
	 * @param databaseIndex  索要查询的数据库
	 * @param start        要查询的开始记录
	 * @param end          查询结果的结束记录
	 * @return
	 * @throws ParseException
	 */
    public Map findPaperBypage(String querystring[],String []fields,String []databaseIndex,int start,int end) throws ParseException{
    	
    	    Analyzer PaperAnalyzer = new IKAnalyzer();
    	    //IndexReader paperreader;
    	    IndexSearcher searcher = null;
    	    IndexReader []	readers = new IndexReader[databaseIndex.length];
    	        MultiReader multiReader = null;
    	       	    
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
    	   		Query multiquery = MultiFieldQueryParser.parse(querystring,fields, PaperAnalyzer);
    	   		
	    	    logger.info("Searching for:{} " , multiquery.toString(fields.toString()));
	    	       	    
	    	    TopDocs results = searcher.search(multiquery,  end);
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
    
  //在fp表中得到所有相关的论文的id 并放近paperIdScore<key：,count:出现的次数>
    public List<Paper> recommand(Paper paper,String paperid) throws ParseException, IOException{
    	
    	List<Paper> papers  = new ArrayList<Paper>();;
    		
	  Map<String,Float> paperIdScore = new HashMap<String, Float>();
	  float keywordWeight = 2f;
	  int sequence = 0;
	 
	  
	  //获得每个作者的文章及打分
	  if(paper.getAuthor()!=null&&!"".equals(paper.getAuthor())){
		  String [] authors = paper.getAuthor().split(",");
	  
	  for(String author:authors){
		
		  sequence++;
     if(authorPapersDao.findPaperIdsByAuthor(author)!=null){
	
    	 String[] PaperIds = authorPapersDao.findPaperIdsByAuthor(author).getPapers().split(",");
			for(String paperId:PaperIds){
	    		if(paperIdScore.containsKey(paperId)){
	    			paperIdScore.put(paperId, paperIdScore.get(paperId)+SearchUtil.getScoreByAuthorSequence(sequence));
	    		}else{
	    				    			
	    			paperIdScore.put(paperId, SearchUtil.getScoreByAuthorSequence(sequence));
	    		}
	    	}
		  
	  }}
    }  
	   //获得关键字对应的的论文
	  if(paper.getKeyword()!=null&&!"".equals(paper.getKeyword())){
		  String [] keywords = paper.getKeyword().split(" ");	  
	  for(String keyword:keywords){
		    
		  if(keywordPaperDao.findPaperIdsByAuthor(keyword)!=null){
		    String [] PaperIds = keywordPaperDao.findPaperIdsByAuthor(keyword).getPapers().split(",");
			for(String paperId:PaperIds){
	    		if(paperIdScore.containsKey(paperId)){
	    			paperIdScore.put(paperId, paperIdScore.get(paperId)+keywordWeight);
	    		}else{
	    				    			
	    			paperIdScore.put(paperId, keywordWeight);
	    		}
	    	}
		  
	  }	 }
	  }
	  sequence = 0;
	//获得关联规则作者每篇文献
	  if(paper.getAuthor()!=null&&!"".equals(paper.getAuthor())){
		  Analyzer analyzer=new WhitespaceAnalyzer();  
	        
	        QueryParser parser = new QueryParser("author", analyzer);   
	        Query query = parser.parse(paper.getAuthor().replaceAll(",", " ")); 
	        Directory dire=FSDirectory.open( Paths.get(IndexConstant.fpAuthorsPapersIndexPath));  
	        IndexReader ir=DirectoryReader.open(dire);  
	        IndexSearcher is=new IndexSearcher(ir);  
	        TopDocs td=is.search(query, 1000);  
	         
	        ScoreDoc[] sds =td.scoreDocs;  
	        for (ScoreDoc sd : sds) {   
	            Document d = is.doc(sd.doc);   
	            if(sd.score>4f){
	            	String[] PaperIds=null;
	            			AuthorsPapers p=authorsPapersDao.findOne(Long.parseLong(d.get("id")));
	            			String paperids=p.getPapers();
	            			PaperIds=paperids.split(",");
	            	for(String paperId:PaperIds){
			    		if(!paperIdScore.containsKey(paperId)){
			    			paperIdScore.put(paperId, sd.score);
			    		}else{
			    			paperIdScore.put(paperId,paperIdScore.get(paperId)+sd.score);
			    		}
			    	}
	            }
	               
	        } 
		  /*
		  String [] authors = paper.getAuthor().split(",");
	  for(String author:authors){
		    sequence++;
		    if( fpAuthorPaperDao.findPaperIdsByFpAuthor(author)!=null){
		        
		    String[] PaperIds = fpAuthorPaperDao.findPaperIdsByFpAuthor(author).getPapers().split(",");
					for(String paperId:PaperIds){
			    		if(!paperIdScore.containsKey(paperId)){
			    			paperIdScore.put(paperId, SearchUtil.getScoreByFPAuthorSequence(sequence));
			    		}else{
			    			paperIdScore.put(paperId,paperIdScore.get(paperId)+SearchUtil.getScoreByFPAuthorSequence(sequence));
			    		}
			    	}   
	  }}
	  
	  */
	  }
	  List<Map.Entry<String,Float>> list = new ArrayList<Map.Entry<String,Float>>(paperIdScore.entrySet());
    Collections.sort(list,new Comparator<Map.Entry<String,Float>>() {
        //对得到的文献候选集升序排序
        public int compare(Entry<String, Float> o1,
                Entry<String, Float> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
        

    });
    int count = 0;
   
    List<String>recommendedPapersId = new ArrayList<String>();
    for(Map.Entry<String,Float> mapping:list){ 
  	  if(!(paper.getId().toString()).equals(mapping.getKey())){
  	  recommendedPapersId.add(mapping.getKey());
  	  count++;
  	  if(count>=11)break;
  	  }
   }    
  
    //封装推荐文献
    for(String id:recommendedPapersId){
        if(id==paperid)continue;//去除兴趣文献
    	if(id!=null&&!"".equals(id)){
    	   	
    	Paper paper1 = paperDao.findOne(Long.valueOf(id));
    	papers.add(paper1);
    	}
    }
    return  papers;   	
    	
    } 
   
  
}
