package com.bjut.service.paper;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Clock;
import com.bjut.entity.HotPaper;
import com.bjut.entity.Paper;
import com.bjut.repository.HotPaperDao;
import com.bjut.repository.PaperDao;
import com.bjut.service.SpecificationFindUtil;


/**
 * HotPaperService 
 * @author yangkaiwen
 *
 */

@Component
public class HotPaperService {
  
	private static Logger logger = LoggerFactory.getLogger(HotPaperService.class);
	@Autowired
	private HotPaperDao hotPaperDao;
	@Autowired
	private PaperDao paperDao;
	private Clock clock = Clock.DEFAULT;

   
	  /**
     * 找到最火的10篇论文
     * @return
     */
    public List<Paper> findHot10Papers(){
    	
    	List<Paper> papers = null;    	
    	PageRequest pageRequest = SpecificationFindUtil.buildPageRequest(1, 10,  "visitTime");
    	List<HotPaper> hotPapers = hotPaperDao.findAll(null, pageRequest).getContent();
    	if(hotPapers.size()!=0){
    		papers = new ArrayList<Paper>();
    		for(HotPaper hotPaper:hotPapers){
        		Paper paper = paperDao.findOne(hotPaper.getPaperid());
        		papers.add(paper);
        	}
    	}
    	logger.info("find top 10 hot papers...the size is {}"+papers.size());
    	return papers;
    	
    }
    
   public void saveVisitPaper(Paper paper){
	   HotPaper hotPaper = hotPaperDao.findByPaperId(paper.getId());
	   if(hotPaper==null){
		   hotPaper = new HotPaper();
		   hotPaper.setPaperid(paper.getId());
		   hotPaper.setVisitTime(1);
		   hotPaper.setVisitDate(new Date());
		   hotPaperDao.save(hotPaper);
		   logger.info("save visit paper log....");
	   }else{
		   hotPaper.setVisitTime(hotPaper.getVisitTime()+1);
		   hotPaperDao.save(hotPaper);
		   logger.info("save visit paper log....");
	   }
   }
    
    
   

}
