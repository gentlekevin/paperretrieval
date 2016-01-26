/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bjut.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bjut.entity.HotPaper;

public interface HotPaperDao extends PagingAndSortingRepository<HotPaper, Long>,
JpaSpecificationExecutor<HotPaper> {
 @Query("from HotPaper t where t.paperid=?1")
public HotPaper findByPaperId(long paperId);
 
}
