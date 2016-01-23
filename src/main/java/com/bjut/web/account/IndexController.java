
package com.bjut.web.account;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjut.search.IndexConstant;
import com.bjut.service.paper.PaperService;
import com.bjut.service.search.SearchService;
import com.bjut.web.formbean.SearchBean;


/**
 * 
 * @author yangkaiwen
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {
	
	@Autowired
	private PaperService paperService;
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value="/index",method = {RequestMethod.GET,RequestMethod.POST})
	public String index() {
		return "/index";
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
