package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "t_paper_author")
public class AuthorPaper extends IdEntity{

	// Fields
 
    private String authors;	
	
	// Constructors

	/** default constructor */
	public AuthorPaper() {
	}
	public AuthorPaper(Long id) {
		this.id = id;
	}
	
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	

}