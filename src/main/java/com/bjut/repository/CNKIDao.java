/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bjut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bjut.entity.CNKI;
import com.bjut.entity.IEEE;
import com.bjut.entity.Paper;
import com.bjut.entity.Springer;


public interface CNKIDao extends PagingAndSortingRepository<CNKI, Long>,
JpaSpecificationExecutor<CNKI> {
	

	
}
