package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_paper")
public class Paper  extends IdEntity{

	// Fields

	
	private String title;//作者
	private String author;//作者
	private String authoraffi;//作者单位
	private String keyword;//关键词
	
	private String abstracts;//摘要
	private String references;//参考文献
	private String sourbase;//来源数据库
	private String soururl;//原文链接
	private String islocal;//是否本地化
	private String downtime;//下载次数
	private String extenone;//扩展一
	private String extentwo;//扩展二
	private String sourjour;//所属期刊
	private String downurl;//直接下载地址
	private String doi;//数字对象标示
	private String type;//资源类型
	private String pubyear;//出版年
	private String pagerange;//页码范围
	private String volume;//卷
	private String issue;//期
	private String foundation;//基金
	private String conferaffi;//学位授予单位
	private String degree;//学位级别
	private String conferyear;//学位授予年限
	private String clc;//分类号
	private String isbn;//ISBN
	private String highlights;//Highlights

	// Constructors

	/** default constructor */
	public Paper() {
	}
	 public Paper(Long id){
	    	this.id  = id;
	    }
	/** full constructor */
	public Paper(String title, String author, String authoraffi,
			String keyword, String abstracts, String references,
			String sourbase, String soururl, String islocal, String downtime,
			String extenone, String extentwo, String sourjour, String downurl,
			String doi, String type, String pubyear, String pagerange,
			String volume, String issue, String foundation, String conferaffi,
			String degree, String conferyear, String clc, String isbn,
			String highlights) {
		this.title = title;
		this.author = author;
		this.authoraffi = authoraffi;
		this.keyword = keyword;
		this.abstracts = abstracts;
		this.references = references;
		this.sourbase = sourbase;
		this.soururl = soururl;
		this.islocal = islocal;
		this.downtime = downtime;
		this.extenone = extenone;
		this.extentwo = extentwo;
		this.sourjour = sourjour;
		this.downurl = downurl;
		this.doi = doi;
		this.type = type;
		this.pubyear = pubyear;
		this.pagerange = pagerange;
		this.volume = volume;
		this.issue = issue;
		this.foundation = foundation;
		this.conferaffi = conferaffi;
		this.degree = degree;
		this.conferyear = conferyear;
		this.clc = clc;
		this.isbn = isbn;
		this.highlights = highlights;
	}

	// Property accessors

	

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthoraffi() {
		return this.authoraffi;
	}

	public void setAuthoraffi(String authoraffi) {
		this.authoraffi = authoraffi;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAbstracts() {
		return this.abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public String getReferences() {
		return this.references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getSourbase() {
		return this.sourbase;
	}

	public void setSourbase(String sourbase) {
		this.sourbase = sourbase;
	}

	public String getSoururl() {
		return this.soururl;
	}

	public void setSoururl(String soururl) {
		this.soururl = soururl;
	}

	public String getIslocal() {
		return this.islocal;
	}

	public void setIslocal(String islocal) {
		this.islocal = islocal;
	}

	public String getDowntime() {
		return this.downtime;
	}

	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}

	public String getExtenone() {
		return this.extenone;
	}

	public void setExtenone(String extenone) {
		this.extenone = extenone;
	}

	public String getExtentwo() {
		return this.extentwo;
	}

	public void setExtentwo(String extentwo) {
		this.extentwo = extentwo;
	}

	public String getSourjour() {
		return this.sourjour;
	}

	public void setSourjour(String sourjour) {
		this.sourjour = sourjour;
	}

	public String getDownurl() {
		return this.downurl;
	}

	public void setDownurl(String downurl) {
		this.downurl = downurl;
	}

	public String getDoi() {
		return this.doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPubyear() {
		return this.pubyear;
	}

	public void setPubyear(String pubyear) {
		this.pubyear = pubyear;
	}

	public String getPagerange() {
		return this.pagerange;
	}

	public void setPagerange(String pagerange) {
		this.pagerange = pagerange;
	}

	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getIssue() {
		return this.issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getFoundation() {
		return this.foundation;
	}

	public void setFoundation(String foundation) {
		this.foundation = foundation;
	}

	public String getConferaffi() {
		return this.conferaffi;
	}

	public void setConferaffi(String conferaffi) {
		this.conferaffi = conferaffi;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getConferyear() {
		return this.conferyear;
	}

	public void setConferyear(String conferyear) {
		this.conferyear = conferyear;
	}

	public String getClc() {
		return this.clc;
	}

	public void setClc(String clc) {
		this.clc = clc;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getHighlights() {
		return this.highlights;
	}

	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}

}