
package com.bjut.web.search;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjut.entity.Paper;
import com.bjut.search.IndexConstant;
import com.bjut.service.paper.HotPaperService;
import com.bjut.service.paper.PaperService;
import com.bjut.service.search.SearchService;
import com.bjut.web.formbean.PageBean;
import com.bjut.web.formbean.SearchBean;


/**
 * 
 * @author yangkaiwen
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {
	private static Logger logger = LoggerFactory.getLogger(SearchController.class);
	@Autowired
	private PaperService paperService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private HotPaperService hotPaperService;
	  
	/**
	 * 用来处search的过程
	 * @param searchBean
	 * @param model
	 * @return 
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST,})
	public String search(@ModelAttribute("searchBean") SearchBean searchBean,Model model) {
		
	
		logger.info("database:{},searchfiled:{},searchwords:{}",searchBean.getDatabase(),searchBean.getSearchfield(),searchBean.getSearchwords());
		logger.info("currentPage:{}",searchBean.getCurrentPage());
		
		if(searchBean.getSearchwords()==null||"".equals(searchBean.getSearchwords())){
			//如果查询关键字为空
			
			List<Paper> hotPapers = hotPaperService.findHot10Papers();
			
			model.addAttribute("searchBean",searchBean);
			model.addAttribute("hotPapers",hotPapers);
		return "/search";
		}
		
		Map map = null;
		final int pageSize=15;//每页显示记录数
		int currentPage = Integer.valueOf(searchBean.getCurrentPage());//当前页
		int start = (currentPage-1)*pageSize+1;//要查询 的起始记录
		int end = currentPage*pageSize;//要查询 的最终记录
		
		String [] databases = null;//要查询的的数据库，默认为三个cnki,springer,ieee
		
		String [] dbIndex = null;//要查询的数据库路径
		String[] fields = null;
		String[] querystrings = null;
		
	
		
		
		//封装searchDatabase ，默认是cnki,springer,ieee		
		if(searchBean.getDatabase()==null||"".equals(searchBean.getDatabase())){
			databases =	(IndexConstant.cnki+","+IndexConstant.springer+","+IndexConstant.ieee).split(",");
		}else{
			databases = (searchBean.getDatabase()).split(",");
		}
		
		//封装indexpath
		dbIndex = 	new String[databases.length];
		 for(int i = 0;i<databases.length;i++){
			
			if("cnki".equals(databases[i])){
				dbIndex[i] = IndexConstant.cnkiIndexPath;
			}else if("springer".equals(databases[i])){
				dbIndex[i] = IndexConstant.springerIndexPath;
			}else if ("ieee".equals(databases[i])){
				dbIndex[i] = IndexConstant.ieeeIndexPath;
			}
		}
		
		//封装searchBeanfiled和queryStrings默认是title,author,keyword
		if(searchBean.getSearchfield()==null||"".equals(searchBean.getSearchfield())){
			fields	 =  (IndexConstant.title+","+IndexConstant.author+","+IndexConstant.keyword).split(",");
			
			querystrings=(searchBean.getSearchwords()+","+searchBean.getSearchwords()+","+searchBean.getSearchwords()).split(",");
		}else{			
			fields = searchBean.getSearchfield().split(",");
			String q =""; 
			for(String s:fields){
				 q=q+ searchBean.getSearchwords()+",";
			}
			querystrings = q.split(",");
		}	
		 double time = 0;
			BigDecimal bd = null;
		try {			
			
			long begintime = System.nanoTime();//纳秒
			Date begin = new Date();
			map = searchService.findPaperBypage(querystrings, fields,dbIndex ,start , end);
			long endtime = System.nanoTime();//纳秒
			Date end1 = new Date();
			BigDecimal diff = BigDecimal.valueOf(endtime-begintime,10);//秒级差值
			 
			 time = diff.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();  
			 time =( end1.getTime()-begin.getTime())/1000f;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		List<Paper> hotPapers = hotPaperService.findHot10Papers();
		model.addAttribute("hotPapers",hotPapers);
		model.addAttribute("pager", new PageBean(pageSize, currentPage, (Integer)map.get("total")));
		model.addAttribute("papers",map.get("papers"));
		model.addAttribute("searchBean",searchBean);
		model.addAttribute("time",time);
		return "/search";
	}	
	
	@RequestMapping(value="/detail",  method = {RequestMethod.GET,RequestMethod.POST})
	public String detail(@ModelAttribute("paperId") String paperId ,Model model) {
	    	
		Paper paper = paperService.findPaperById(Long.valueOf(paperId));
		hotPaperService.saveVisitPaper(paper);//用作hotpaper用
		List<Paper> recommandPapers = null;
		try {
			recommandPapers = searchService.recommand(paper,paperId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		model.addAttribute("paper", paper);
		model.addAttribute("recommandPapers", recommandPapers);
		//model.addAttribute("hotPapers", hotPapers);
		
		return "paperDetail";
	}
	

	/**
	 * 
	 * 用来封装searchform属性和值
	 */
	@ModelAttribute
	public void getUser( Model model) {
		
			model.addAttribute("searchBean",new SearchBean());
			
	}
}
