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
    
    public Paper findPaperById(long id){
    	return paperDao.findOne(id);
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
