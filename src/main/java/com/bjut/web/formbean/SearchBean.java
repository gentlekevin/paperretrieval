package com.bjut.web.formbean;
/**
 * 封装search相关信息
 * @author yangkaiwen
 *
 */
public class SearchBean {
 
	private String database;//来源数据库
	private String searchfield;//搜索范围
	private String searchwords;//搜索关键字
	
	
	public SearchBean() {
		super();
	}


	public SearchBean(String database, String searchfield, String searchwords) {
		super();
		this.database = database;
		this.searchfield = searchfield;
		this.searchwords = searchwords;
	}


	public String getDatabase() {
		return database;
	}


	public void setDatabase(String database) {
		this.database = database;
	}


	public String getSearchfield() {
		return searchfield;
	}


	public void setSearchfield(String searchfield) {
		this.searchfield = searchfield;
	}


	public String getSearchwords() {
		return searchwords;
	}


	public void setSearchwords(String searchwords) {
		this.searchwords = searchwords;
	}
	
	
}
