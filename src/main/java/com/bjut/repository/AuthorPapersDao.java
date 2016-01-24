package com.bjut.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bjut.entity.AuthorPapers;

public interface AuthorPapersDao extends PagingAndSortingRepository<AuthorPapers, Long>,
JpaSpecificationExecutor<AuthorPapers> {

	@Query(" from  AuthorPapers a where a.author=?1")
	public AuthorPapers findPaperIdsByAuthor(String author);
	
}
