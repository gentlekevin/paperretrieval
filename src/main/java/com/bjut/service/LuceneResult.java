package com.bjut.service;


import java.util.List;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import com.bjut.entity.Paper;

public class LuceneResult {

	
	protected Page<Paper> findAllPaperByPage(List<Paper> papers, Pageable pageable, Long total ) {
    
		return new PageImpl<Paper>(papers, pageable, total);
	}

}
