
package com.bjut.web.account;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 
 * @author yangkaiwen
 */
@Controller
@RequestMapping(value = "/index")
public class ResultController {

	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String login(HttpSession httpSession) {
	String id = httpSession.getId();
	
	return "/index";
	}

}
