package com.bjut.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bjut.entity.AuthorPapers;
import com.bjut.entity.AuthorsPapers;

public interface AuthorsPapersDao extends PagingAndSortingRepository<AuthorsPapers, Long>,
JpaSpecificationExecutor<AuthorsPapers> {

	
	
}
