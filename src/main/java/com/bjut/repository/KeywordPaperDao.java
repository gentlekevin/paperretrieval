/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bjut.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.bjut.entity.KeywordPaper;



public interface KeywordPaperDao extends PagingAndSortingRepository<KeywordPaper, Long>,
JpaSpecificationExecutor<KeywordPaper> {
	
	@Query(" from  KeywordPaper a where a.keyword=?1")
	public KeywordPaper findPaperIdsByAuthor(String keyword);
	
}
