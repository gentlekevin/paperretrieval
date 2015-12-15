package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "t_authors_papers")
public class AuthorsPapers extends IdEntity{

	// Fields
 
    private String authors;	
	
    private String papers;
	// Constructors

	/** default constructor */
	public AuthorsPapers() {
	}
	public AuthorsPapers(Long id) {
		this.id = id;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getPapers() {
		return papers;
	}
	public void setPapers(String papers) {
		this.papers = papers;
	}
	
	
	

}