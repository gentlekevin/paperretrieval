
package com.bjut.web.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.bjut.service.paper.PaperService;
import com.bjut.web.formbean.SearchBean;


/**
 * 
 * @author yangkaiwen
 */
@Controller
@RequestMapping(value = "/")
public class LuceneIndexController {
	
	@Autowired
	private PaperService paperService;
	
	
	@RequestMapping(value="/indexs",method = {RequestMethod.GET,RequestMethod.POST})
	public String index() {
		return "/index";
	}
	/**
	 * 用来处search的过程
	 * @param searchBean
	 * @param model
	 * @return 
	 */
	@RequestMapping(value="/results",method = {RequestMethod.GET,RequestMethod.POST,})
	public String search(@ModelAttribute("searchBean") SearchBean searchBean,Model model) {
		
		System.out.println("database:"+searchBean.getDatabase());
		System.out.println("searchfiled:"+searchBean.getSearchfield());
		System.out.println("searchwords:"+searchBean.getSearchwords());
		
		//paperService.findPapersByPage(queryString, field, pageNumber, pageSize)
		
		
		return "/result";
	}
	
	
	@RequestMapping(value="/createAuthorPaperIndex",method = {RequestMethod.GET,RequestMethod.POST,})
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
