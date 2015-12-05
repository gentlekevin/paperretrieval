package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_keyword_search")
public class KeywordSearch  extends IdEntity {
	
	
	private String keyword;	// 关键词
	private String paperid;	// 论文id集合
	private int papernum;	// 记录包含的论文数
	
	public KeywordSearch () {
	}
	
	public KeywordSearch(Long id){
		this.id = id;
	}
	public KeywordSearch (String keyword, String paperid, int papernum) {
		this.keyword = keyword;
		this.paperid = paperid;
		this.papernum = papernum;
	}
	

	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeyword() {
		return keyword;
	}
	
	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}
	public String getPaperid() {
		return paperid;
	}

	public void setPapernum(int papernum) {
		this.papernum = papernum;
	}

	public int getPapernum() {
		return papernum;
	}

}
