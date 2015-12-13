package com.bjut.service.paper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Clock;
import com.bjut.entity.AuthorPaper;
import com.bjut.entity.Paper;
import com.bjut.repository.AuthorPaperDao;
import com.bjut.repository.PaperDao;
import com.bjut.service.SpecificationFindUtil;
import com.bjut.service.account.AccountService;

/**
 * paperService 
 * @author yangkaiwen
 *
 */

@Component
public class PaperService {
  
	private static Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	private PaperDao paperDao;
	@Autowired
	private AuthorPaperDao authorPaperDao;
	private Clock clock = Clock.DEFAULT;
    public List<Paper> findAllPaper(){
    	
    	return (List<Paper>) paperDao.findAll();
    	
    }	
    public void indexPapers(){
    	String indexPath = "d:/fpindex";
    	Analyzer analyzer = null;
    	IndexWriterConfig iwc = null;
       	IndexWriter writer =  null;
    	MMapDirectory mdir = null;
    	try {
    		
			
			 Path path  = Paths.get(indexPath);
			 mdir = new MMapDirectory(path);
			 analyzer = new  SmartChineseAnalyzer();
	         iwc = new IndexWriterConfig(analyzer);
	         iwc.setOpenMode(OpenMode.CREATE);
	         iwc.setRAMBufferSizeMB(1024);
	         writer = new IndexWriter(mdir, iwc);
	    	 boolean flag = true;
	         int count = 0;
	         int pagesize = 100;
	         int pageNum = 1;
	       
	         Page<AuthorPaper> papers = null;
	         logger.info("index begin,begin time:"+clock.getCurrentTimeInMillis());
	         while(flag){
	        	  papers = this.findAuthorPaperByNum(pagesize, pageNum);
	          	  if(papers.getSize()<100){
	          		  flag = false;
	          		count= count+papers.getSize();
	          	  }
	          	  pageNum++;
	          	  count = count+100;
	              
	 	         for(AuthorPaper paper : papers){
	 	       //index every paper
	 	        	 indexDoc(writer, paper);	 
	 	         }
	 	         if(count>100000){
	 	        	 break;
	 	         }
	 	        logger.info("paper index{}pages,total:{}records--------",pageNum,count);
	         }       
	     
        //close writer
        writer.close();
        logger.info("index end,end time:"+clock.getCurrentTimeInMillis());
    	} catch (IOException e) {
    		logger.error("someting wrong:{}", e);
			e.printStackTrace();
		}
    
    }
    // Indexes a single paper
    static void indexDoc(IndexWriter writer, AuthorPaper paper) throws IOException {
     
        // make a new, empty document
        Document doc = new Document();
        
        doc.add(new LongField("id",paper.getId() , Field.Store.YES));
        if(paper.getAuthors()!=null){
        	doc.add(new TextField("author", paper.getAuthors(),Field.Store.NO));	
        }      
         
          writer.addDocument(doc);
        
      
    }
    
    public Page<Paper> findPapersByPage(String queryString,String field,int pageNumber, int pageSize) 
    		throws IOException, ParseException{
    	
    	    String index = "./index";
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
    	    IndexSearcher searcher = new IndexSearcher(reader);
    	    Analyzer analyzer = new SmartChineseAnalyzer();
     	    QueryParser parser = new QueryParser(field, analyzer);
    	    Query query = parser.parse(queryString);
    	    logger.info("Searching for: " + query.toString(field));
    	   // Collect enough docs to show 10 pages
    	    TopDocs results = searcher.search(query, 10 * pageSize);
    	    ScoreDoc[] hits = results.scoreDocs;
    	    int numTotalHits = results.totalHits;
    	    List <Paper> papers = new ArrayList<Paper>();
    	    int start = pageNumber*pageSize;
    	    int end = Math.min(hits.length, start + pageSize);
    	    Paper paper = null;
    	    Document doc = null;
    	    String id = null;
    	      for (int i = start; i < end; i++) {
    	           doc = searcher.doc(hits[i].doc);
    	         id = doc.get("id");
    	         if(id!=null){
    	        	 paper = paperDao.findOne(Long.parseLong(id));
    	         } 
    	         papers.add(paper);         
    	        }
    	       reader.close();
    	
    	return getPapers(papers, pageNumber, pageSize, (long)numTotalHits);
    	
    }
   
      
      
  //分页查询lucene
    public Page<Paper> getPapers(List<Paper> papers,int pageNumber, int pageSize,Long total) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, null);
		return new PageImpl<Paper>(papers, pageRequest, total);
	}
    
    
    //分页查询数据库
    public Page <Paper> findPapersByNum(int pageSize,int pageNumber){
    	
    	 PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(pageNumber, pageSize, null);
    	 return (Page<Paper>) paperDao.findAll(null,pageRequest);
    	
    }
    
    //分页查询数据库
    public Page <AuthorPaper> findAuthorPaperByNum(int pageSize,int pageNumber){
    	
    	 PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(pageNumber, pageSize, null);
    	 return (Page<AuthorPaper>) authorPaperDao.findAll(null,pageRequest);
    	
    }
    
    
   

}
