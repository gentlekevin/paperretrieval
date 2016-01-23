/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bjut.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.bjut.entity.temp;



public interface TempDao extends PagingAndSortingRepository<temp, Long>,
JpaSpecificationExecutor<temp> {
	

	
}
