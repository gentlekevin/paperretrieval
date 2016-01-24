package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_fp_author_papers")
public class FpAuthorPaper extends IdEntity{

	private String author;
	private String papers;
	
	
	public FpAuthorPaper() {
	
	}
	public FpAuthorPaper(Long id){
    	this.id  = id;
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
	public FpAuthorPaper(String author, String papers) {
		super();
		this.author = author;
		this.papers = papers;
	}
	
	
}
