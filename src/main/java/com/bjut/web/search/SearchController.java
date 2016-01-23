
package com.bjut.web.search;

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

import com.bjut.search.IndexConstant;
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
			model.addAttribute("searchBean",searchBean);
		return "/search";
		}
		
		
		Map map = null;
		final int pageSize=15;
		int currentPage = Integer.valueOf(searchBean.getCurrentPage());
		int start = (currentPage-1)*pageSize+1;
		int end = currentPage*pageSize;
		String [] databases = searchBean.getDatabase().split(",");
		String [] dbIndex = new String[databases.length];
		for(int i = 0;i<databases.length;i++){
			
			if("cnki".equals(databases[i])){
				dbIndex[i] = IndexConstant.cnkiIndexPath;
			}else if("springer".equals(databases[i])){
				dbIndex[i] = IndexConstant.springerIndexPath;
			}else if ("ieee".equals(databases[i])){
				dbIndex[i] = IndexConstant.ieeeIndexPath;
			}
		}
		try {
			map = searchService.findPaperBypage(searchBean.getSearchwords(), searchBean.getSearchfield(),dbIndex ,start , end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		model.addAttribute("pager", new PageBean(pageSize, currentPage, (Integer)map.get("total")));
		model.addAttribute("papers",map.get("papers"));
		model.addAttribute("searchBean",searchBean);
		
		return "/search";
	}
	
	
	@RequestMapping(value="/createAuthorPaperIndex",method = {RequestMethod.GET,RequestMethod.POST})
	public void createIndex() {
	    
		paperService.indexPapers();
		
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
