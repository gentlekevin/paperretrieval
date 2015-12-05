package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_database")
public class Database extends IdEntity {
	
	private String name;	// 资源数据库名
	private String homeurl;	// 主页url
	private String totalnum;	// 包含的论文数
	private String ulastdate;	// 最后更新时间
	
	public Database () {
	}
	

	public Database(Long id) {
		this.id = id;
	}
	public Database (String name, String homeurl,
			String totalnum, String ulastdate) {
		
		this.name = name;
		this.homeurl = homeurl;
		this.totalnum = totalnum;
		this.ulastdate = ulastdate;
	}
	


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getHomeurl() {
		return homeurl;
	}
	public void setHomeurl(String homeurl) {
		this.homeurl = homeurl;
	}

	public String getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(String totalnum) {
		this.totalnum = totalnum;
	}

	public void setUlastdate(String ulastdate) {
		this.ulastdate = ulastdate;
	}

	public String getUlastdate() {
		return ulastdate;
	}

}