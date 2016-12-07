package com.bjut.service.search;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.MMapDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Clock;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.bjut.entity.AuthorsPapers;
import com.bjut.entity.CNKI;
import com.bjut.entity.IEEE;
import com.bjut.entity.Springer;
import com.bjut.repository.AuthorsPapersDao;
import com.bjut.repository.CNKIDao;
import com.bjut.repository.AuthorPapersDao;
import com.bjut.repository.IEEEDao;
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
 * 索引相关
 * @author yangkaiwen
 * 
 */

@Component
public class IndexService {

	private static Logger logger = LoggerFactory.getLogger(IndexService.class);
	private Clock clock = Clock.DEFAULT;
	@Autowired
	private CNKIDao cnkiDao;
	@Autowired
	private SpringerDao springerDao;
	@Autowired
	private IEEEDao ieeeDao;
	@Autowired
	private AuthorsPapersDao authorsPapersDao;

	 /**
	  * 建立IEEE文献的索引
	  */
	public void CreateIeeeIndex() {

		Analyzer analyzer = null;
		IndexWriterConfig iwc = null;
		IndexWriter writer = null;
		MMapDirectory mdir = null;
		try {

			Path path = Paths.get(IndexConstant.ieeeIndexPath);
			mdir = new MMapDirectory(path);
			analyzer = new IKAnalyzer();
			iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			iwc.setRAMBufferSizeMB(2048);
			writer = new IndexWriter(mdir, iwc);

			boolean flag = true;
			int count = 0;
			int pagesize = 1000;
			int pageNum = 1;
			//得到document和filed的单例
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

			Page<IEEE> papers = null;
			logger.info("index begin,begin time:"
					+ clock.getCurrentTimeInMillis());
			while (flag) {
				papers = this.findIeeePage(pagesize, pageNum);
				if (papers.getContent().size() < 1000) {
					flag = false;
					count = count + papers.getSize();
				} else {
					count = count + 1000;
				}
				pageNum++;

				for (IEEE paper : papers) {
					//对document的filed值改变，达到重用的效果，减少GC
					if (paper.getAuthor() != null) {
						author.setStringValue(paper.getAuthor());
					}
					if (paper.getTitle() != null) {
						title.setStringValue(paper.getTitle());
					}
					if (paper.getKeyword() != null) {
						keyword.setStringValue(paper.getKeyword());
					}
					if (paper.getSource() != null) {
						source.setStringValue(paper.getSource());
					}

					idField.setLongValue(paper.getId());

					writer.addDocument(doc);
				}

				logger.info("paper ieee index{}pages,{},total:{}records--------",pageNum, papers.getContent().size(), count);
			}
			writer.forceMerge(1);
			// close writer
			writer.close();
			logger.info("index end,end time:" + clock.getCurrentTimeInMillis());
		} catch (IOException e) {
			logger.error("someting wrong:{}", e);
			e.printStackTrace();
		}
	}
 /**
  * 建立sprigner文献的索引
  */
	public void CreateSpringerIndex() {

		Analyzer analyzer = null;
		IndexWriterConfig iwc = null;
		IndexWriter writer = null;
		MMapDirectory mdir = null;
		try {

			Path path = Paths.get(IndexConstant.springerIndexPath);
			mdir = new MMapDirectory(path);
			analyzer = new IKAnalyzer();
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

			Page<Springer> papers = null;
			logger.info("index begin,begin time:"
					+ clock.getCurrentTimeInMillis());
			while (flag) {
				papers = this.findSpringerByNum(pagesize, pageNum);
				if (papers.getContent().size() < 1000) {
					flag = false;
					count = count + papers.getSize();
				} else {
					count = count + 1000;
				}
				pageNum++;

				for (Springer paper : papers) {
					if (paper.getAuthor() != null) {
						author.setStringValue(paper.getAuthor());
					}
					if (paper.getTitle() != null) {
						title.setStringValue(paper.getTitle());
					}
					if (paper.getKeyword() != null) {
						keyword.setStringValue(paper.getKeyword());
					}
					if (paper.getSource() != null) {
						source.setStringValue(paper.getSource());
					}

					idField.setLongValue(paper.getId());

					writer.addDocument(doc);
				}

				logger.info("paper  springer index{}pages,total:{}records--------",pageNum, count);
			}
			writer.forceMerge(1);
			// close writer
			writer.close();
			logger.info("index end,end time:" + clock.getCurrentTimeInMillis());
		} catch (IOException e) {
			logger.error("someting wrong:{}", e);
			e.printStackTrace();
		}

	}
	 /**
	  * 建立知网文献的索引
	  */
	public void CreateCNKIIndex() {

		Analyzer analyzer = null;
		IndexWriterConfig iwc = null;
		IndexWriter writer = null;
		MMapDirectory mdir = null;
		try {

			Path path = Paths.get(IndexConstant.cnkiIndexPath);
			mdir = new MMapDirectory(path);
			analyzer = new IKAnalyzer();
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
			logger.info("index begin,begin time:"
					+ clock.getCurrentTimeInMillis());
			while (flag) {
				papers = this.findCnkiByNum(pagesize, pageNum);
				if (papers.getContent().size() < 1000) {
					flag = false;
					count = count + papers.getSize();
				} else {
					count = count + 1000;
				}
				pageNum++;

				for (CNKI paper : papers) {
					if (paper.getAuthor() != null) {
						author.setStringValue(paper.getAuthor());
					}
					if (paper.getTitle() != null) {
						title.setStringValue(paper.getTitle());
					}
					if (paper.getKeyword() != null) {
						keyword.setStringValue(paper.getKeyword());
					}
					if (paper.getSource() != null) {
						source.setStringValue(paper.getSource());
					}

					idField.setLongValue(paper.getId());

					writer.addDocument(doc);
				}
				logger.info("paper cnki index{}pages,total:{}records--------",
						pageNum, count);
			}
			writer.forceMerge(1);
			// close writer
			writer.close();
			logger.info("index end,end time:" + clock.getCurrentTimeInMillis());
		} catch (IOException e) {
			logger.error("someting wrong:{}", e);
			e.printStackTrace();
		}

	}
	 /**
	  * 建立FP作者频繁项集索引
	  */
	public void CreateFPAuthorsPapersIndex() {

		Analyzer analyzer = null;
		IndexWriterConfig iwc = null;
		IndexWriter writer = null;
		MMapDirectory mdir = null;
		try {

			Path path = Paths.get(IndexConstant.fpAuthorsPapersIndexPath);
			mdir = new MMapDirectory(path);
			analyzer = new WhitespaceAnalyzer();
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
			
			TextField author = SingleAuthorField.getInstance();
			doc.add(idField);
			doc.add(author);
			writer.addDocument(doc);

			Page<AuthorsPapers> papers = null;
			logger.info("index begin,begin time:"
					+ clock.getCurrentTimeInMillis());
			while (flag) {
				papers = this.findAuthorsPapersByNum(pagesize, pageNum);
				if (papers.getContent().size() < 1000) {
					flag = false;
					count = count + papers.getSize();
				} else {
					count = count + 1000;
				}
				pageNum++;

				for (AuthorsPapers paper : papers) {
					if (paper.getAuthors() != null) {
						author.setStringValue(paper.getAuthors().replaceAll(",", " "));
					}
					
					idField.setLongValue(paper.getId());

					writer.addDocument(doc);
				}
				logger.info("paper cnki index{}pages,total:{}records--------",
						pageNum, count);
			}
			writer.forceMerge(1);
			// close writer
			writer.close();
			logger.info("index end,end time:" + clock.getCurrentTimeInMillis());
		} catch (IOException e) {
			logger.error("someting wrong:{}", e);
			e.printStackTrace();
		}

	}
	
	
	
	// 分页查询数据库
	public Page<IEEE> findIeeePage(int pageSize, int pageNumber) {

		PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(
				pageNumber, pageSize, null);
		return (Page<IEEE>) ieeeDao.findAll(null, pageRequest);

	}

	// 分页查询数据库
	public Page<Springer> findSpringerByNum(int pageSize, int pageNumber) {

		PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(
				pageNumber, pageSize, null);
		return (Page<Springer>) springerDao.findAll(null, pageRequest);

	}

	// 分页查询数据库
	public Page<CNKI> findCnkiByNum(int pageSize, int pageNumber) {

		PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(
				pageNumber, pageSize, null);
		return (Page<CNKI>) cnkiDao.findAll(null, pageRequest);

	}
	

	
	// 分页查询数据库
		public Page<AuthorsPapers> findAuthorsPapersByNum(int pageSize, int pageNumber) {

			PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(
					pageNumber, pageSize, "id");
			return (Page<AuthorsPapers>) authorsPapersDao.findAll(null, pageRequest);

		}

}
