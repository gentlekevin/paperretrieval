package com.bjut.service.paper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Clock;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.bjut.entity.AuthorPaper;
import com.bjut.entity.CNKI;
import com.bjut.entity.IEEE;
import com.bjut.entity.Paper;
import com.bjut.entity.Springer;
import com.bjut.entity.temp;
import com.bjut.repository.AuthorPaperDao;
import com.bjut.repository.CNKIDao;
import com.bjut.repository.IEEEDao;
import com.bjut.repository.PaperDao;
import com.bjut.repository.SpringerDao;
import com.bjut.repository.TempDao;
import com.bjut.search.field.SingleAuthorField;
import com.bjut.search.field.SingleDucument;
import com.bjut.search.field.SingleIdField;
import com.bjut.search.field.SingleKeywordField;
import com.bjut.search.field.SingleSourceField;
import com.bjut.search.field.SingleTitleField;
import com.bjut.service.SpecificationFindUtil;
import com.bjut.service.account.AccountService;


/**
 * paperService 
 * @author yangkaiwen
 *
 */

@Component
public class PaperService {
  
	private static Logger logger = LoggerFactory.getLogger(PaperService.class);
	@Autowired
	private PaperDao paperDao;
	@Autowired
	private CNKIDao cnkiDao;
	@Autowired
	private SpringerDao springerDao;
	@Autowired
	private TempDao ieeeDao;
	@Autowired
	private TempDao tempDao;
	@Autowired
	private AuthorPaperDao authorPaperDao;
	private Clock clock = Clock.DEFAULT;
    public List<Paper> findAllPaper(){
    	
    	return (List<Paper>) paperDao.findAll();
    	
    }	
    public void createIndex(){
    	String springerIndex = "d:/LuceneIndex/springerIndex";
    	String ieeeIndex = "d:/LuceneIndex/ieeeIndex";
    	String cnkiIndex = "d:/LuceneIndex/ckniIndex";
    }
    
    
    
    /*
    public void CreateIeeeIndex(){
    	
    	   	
    	
        String indexPath =  "G:/LuceneIndex/ieeeIndex";
    	Analyzer analyzer = null;
    	IndexWriterConfig iwc = null;
       	IndexWriter writer =  null;
    	MMapDirectory mdir = null;
    	try {	
    				
			 Path path  = Paths.get(indexPath);
			 mdir = new MMapDirectory(path);
			 analyzer =  new IKAnalyzer();
	         iwc = new IndexWriterConfig(analyzer);
	         iwc.setOpenMode(OpenMode.CREATE);
	         iwc.setRAMBufferSizeMB(2048);  
	         writer = new IndexWriter(mdir, iwc);
	     
	    	 boolean flag = true;
	         int count = 0;
	         int pagesize = 1000;
	         int pageNum = 1;
	       
	         Page<IEEE> papers = null;
	         logger.info("index begin,begin time:"+clock.getCurrentTimeInMillis());
	         while(flag){
	        	  papers = this.findIEEEByNum(pagesize, pageNum);
	          	  if(papers.getContent().size()<1000){
	          		  flag = false;
	          		count= count+papers.getSize();
	          	  }else{
	          		count = count+1000;
	          	  }
	          	  pageNum++;
	          	  
	          
	 	         for(IEEE paper : papers){
	 	       //index every paper
	 	        	 indexIEEEDoc(writer, paper);	 
	 	         }
	 	   
	 	        logger.info("paper ieee index{}pages,{},total:{}records--------",pageNum,papers.getContent().size(),count);
	         }       
	         writer.forceMerge(1);
        //close writer
        writer.close();
        logger.info("index end,end time:"+clock.getCurrentTimeInMillis());
    	} catch (IOException e) {
    		logger.error("someting wrong:{}", e);
			e.printStackTrace();
		}
    
    }
    
 public void CreateSpringerIndex(){
    	
       
      	String indexPath = "G:/LuceneIndex/springerIndex";
    	Analyzer analyzer = null;
    	IndexWriterConfig iwc = null;
       	IndexWriter writer =  null;
    	MMapDirectory mdir = null;
    	try {	
    				
			 Path path  = Paths.get(indexPath);
			 mdir = new MMapDirectory(path);
			 analyzer = new  IKAnalyzer();
	         iwc = new IndexWriterConfig(analyzer);
	         iwc.setOpenMode(OpenMode.CREATE);
	         iwc.setRAMBufferSizeMB(2048);    
	         writer = new IndexWriter(mdir, iwc);
	     
	    	 boolean flag = true;
	         int count = 0;
	         int pagesize = 1000;
	         int pageNum = 1;
	       
	         Page<Springer> papers = null;
	         logger.info("index begin,begin time:"+clock.getCurrentTimeInMillis());
	         while(flag){
	        	  papers = this.findSpringerByNum(pagesize, pageNum);
	        	  if(papers.getContent().size()<1000){
	          		  flag = false;
	          		count= count+papers.getSize();
	          	  }else{
	          		count = count+1000;
	          	  }
	          	  pageNum++;
	        
	          
	 	         for(Springer paper : papers){
	 	       //index every paper
	 	        	 indexSpringerDoc(writer, paper);	 
	 	         }
	 	     
	 	       
	 	        logger.info("paper  springer index{}pages,total:{}records--------",pageNum,count);
	         }       
	         writer.forceMerge(1);
        //close writer
        writer.close();
        logger.info("index end,end time:"+clock.getCurrentTimeInMillis());
    	} catch (IOException e) {
    		logger.error("someting wrong:{}", e);
			e.printStackTrace();
		}
    
    }
 */
 public void CreateCNKIIndex(){
 	
     
   	String indexPath = "G:/LuceneIndex/cnkiIndex";
 	Analyzer analyzer = null;
 	IndexWriterConfig iwc = null;
    	IndexWriter writer =  null;
 	MMapDirectory mdir = null;
 	try {	
 				
			 Path path  = Paths.get(indexPath);
			 mdir = new MMapDirectory(path);
			 analyzer = new  IKAnalyzer();
	         iwc = new IndexWriterConfig(analyzer);
	         iwc.setOpenMode(OpenMode.CREATE);
	         iwc.setRAMBufferSizeMB(2048);       
	         writer = new IndexWriter(mdir, iwc);
	     
	    	 boolean flag = true;
	         int count = 0;
	         int pagesize = 1000;
	         int pageNum = 1;
	         Document doc = SingleDucument.getInstance();
	         LongField idField = SingleIdField.getInstance();
	         StringField source = SingleSourceField.getInstance();
	         TextField title = SingleTitleField.getInstance();
	         TextField keyword = SingleKeywordField.getInstance();
	         TextField author = SingleAuthorField.getInstance();
	         doc.add(idField);
	         doc.add(source);
	         doc.add(title);
	         doc.add(keyword);
	         doc.add(author);
	         writer.addDocument(doc);
	         
	         
	         Page<CNKI> papers = null;
	         logger.info("index begin,begin time:"+clock.getCurrentTimeInMillis());
	         while(flag){
	        	  papers = this.findCnkiByNum(pagesize, pageNum);
	        	  if(papers.getContent().size()<1000){
	          		  flag = false;
	          		count= count+papers.getSize();
	          	  }else{
	          		count = count+1000;
	          	  }
	          	  pageNum++;
	                  
	 	         for(CNKI paper : papers){
	 	        	if(paper.getAuthor()!=null){
	 	        		  author.setStringValue(paper.getAuthor());
	 	           }
	 	           if(paper.getTitle()!=null){
	 	        	  title.setStringValue(paper.getTitle());
	 	           }
	 	           if(paper.getKeyword()!=null){
	 	        	  keyword.setStringValue(paper.getKeyword());
	 	           }
	 	           if(paper.getSource()!=null){
	 	           	source.setStringValue(paper.getSource());
	 	           }   	 
	 	        	 
	 	        	  idField.setLongValue(paper.getId());
	 		        	       
	 		         writer.addDocument(doc);
	 	         }
	 	       
	 	        logger.info("paper cnki index{}pages,total:{}records--------",pageNum,count);
	         }       
	         writer.forceMerge(1);
     //close writer
     writer.close();
     logger.info("index end,end time:"+clock.getCurrentTimeInMillis());
 	} catch (IOException e) {
 		logger.error("someting wrong:{}", e);
			e.printStackTrace();
		}
 
 }
 
    public void indexPapers(){
    	//CreateIeeeIndex();
    	//CreateSpringerIndex();
    	CreateCNKIIndex();
    }
    
    
    
    
    
    
    
    // Indexes a single paper
    static void indexIEEEDoc(IndexWriter writer, IEEE paper) throws IOException {
     
        
    	// make a new, empty document
        Document doc = new Document();
        doc.add(new LongField("id",paper.getId() , Field.Store.YES));
        if(paper.getAuthor()!=null){
        	doc.add(new TextField("author", paper.getAuthor(),Field.Store.NO));	
        }
        if(paper.getTitle()!=null){
        	doc.add(new TextField("title", paper.getTitle(),Field.Store.NO));	
        }
        if(paper.getKeyword()!=null){
        	doc.add(new TextField("keyword", paper.getKeyword(),Field.Store.NO));	
        }
        if(paper.getSource()!=null){
        	doc.add(new StringField("source", paper.getSource(),Field.Store.NO));	
        }         
          writer.addDocument(doc);
        } 
      
 static void indexSpringerDoc(IndexWriter writer, Springer paper) throws IOException {
     
        
    	// make a new, empty document
        Document doc = new Document();
        doc.add(new LongField("id",paper.getId() , Field.Store.YES));
        if(paper.getAuthor()!=null){
        	doc.add(new TextField("author", paper.getAuthor(),Field.Store.NO));	
        }
        if(paper.getTitle()!=null){
        	doc.add(new TextField("title", paper.getTitle(),Field.Store.NO));	
        }
        if(paper.getKeyword()!=null){
        	doc.add(new TextField("keyword", paper.getKeyword(),Field.Store.NO));	
        }
        if(paper.getSource()!=null){
        	doc.add(new StringField("source", paper.getSource(),Field.Store.NO));	
        }         
          writer.addDocument(doc);
        } 
 static void indexcnkiDoc(IndexWriter writer,Document doc, CNKI paper) throws IOException {
     
     
 	// make a new, empty document
   
     doc.add(new LongField("id",paper.getId() , Field.Store.YES));
     if(paper.getAuthor()!=null){
     	doc.add(new TextField("author", paper.getAuthor(),Field.Store.NO));	
     }
     if(paper.getTitle()!=null){
     	doc.add(new TextField("title", paper.getTitle(),Field.Store.NO));	
     }
     if(paper.getKeyword()!=null){
     	doc.add(new TextField("keyword", paper.getKeyword(),Field.Store.NO));	
     }
     if(paper.getSource()!=null){
     	doc.add(new StringField("source", paper.getSource(),Field.Store.NO));	
     }         
       writer.addDocument(doc);
     } 
    /*
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
   
      */
      
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
   
    //分页查询数据库
    public Page <Springer> findSpringerByNum(int pageSize,int pageNumber){
    	
    	 PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(pageNumber, pageSize, null);
    	 return (Page<Springer>) springerDao.findAll(null,pageRequest);
    	
    }
    //分页查询数据库
    public Page <CNKI> findCnkiByNum(int pageSize,int pageNumber){
    	
    	 PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(pageNumber, pageSize, null);
    	 return (Page<CNKI>) cnkiDao.findAll(null,pageRequest);
    	
    }
    
    //分页查询数据库
    public Page <AuthorPaper> findAuthorPaperByNum(int pageSize,int pageNumber){
    	
    	 PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(pageNumber, pageSize, null);
    	 return (Page<AuthorPaper>) authorPaperDao.findAll(null,pageRequest);
  
    	
    }
    
    
   

}
