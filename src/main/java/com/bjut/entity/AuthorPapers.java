package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name = "t_author_papers")
public class AuthorPapers extends IdEntity{

	// Fields
 
    private String author;	
	
    private String papers;
	// Constructors

    
	/** default constructor */
	public AuthorPapers() {
	}
	public AuthorPapers(Long id) {
		this.id = id;
	}
	
	
	public AuthorPapers(String author, String papers) {
		super();
		this.author = author;
		this.papers = papers;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPapers() {
		return papers;
	}
	public void setPapers(String papers) {
		this.papers = papers;
	}
	
	
	

}