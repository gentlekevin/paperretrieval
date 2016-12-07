/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bjut.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.bjut.entity.FpAuthorPaper;


public interface FpAuthorPaperDao extends PagingAndSortingRepository<FpAuthorPaper, Long>,
JpaSpecificationExecutor<FpAuthorPaper> {
	
	@Query(" from  FpAuthorPaper a where a.author=?1")
	public FpAuthorPaper findPaperIdsByFpAuthor(String author);
	
	
}
