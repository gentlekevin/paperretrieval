package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_keyword_papers")
public class KeywordPaper  extends IdEntity {
	
	
	private String keyword;	// 关键词
	private String papers;	// 论文id集合
	
	
	public KeywordPaper() {
		
	}
public KeywordPaper(long id) {
		this.id = id;
	}
	
	public KeywordPaper(String keyword, String papers) {
		super();
		this.keyword = keyword;
		this.papers = papers;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getPapers() {
		return papers;
	}
	public void setPapers(String papers) {
		this.papers = papers;
	}
	
	
	

	
	
}
