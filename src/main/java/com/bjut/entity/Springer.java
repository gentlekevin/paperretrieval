package com.bjut.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_springer")
public class Springer  extends IdEntity{

	// Fields

	
	private String title;//作者
	private String author;//作者
	private String keyword;//关键词
	private String source;
	 
	

	/** default constructor */
	public Springer() {
	}
	 public Springer(Long id){
	    	this.id  = id;
	    }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}


}