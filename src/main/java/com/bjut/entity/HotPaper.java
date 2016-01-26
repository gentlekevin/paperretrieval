package com.bjut.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_hot_paper")
public class HotPaper {

	// Fields
     private long id;
	 private long paperid;
	 private Date visitDate;
	 private long visitTime;
	

	/** default constructor */
	public HotPaper() {
	}
	 public HotPaper(Long id){
	    	this.id  = id;
	    }
	public long getPaperid() {
		return paperid;
	}
	public void setPaperid(long paperid) {
		this.paperid = paperid;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public long getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(long visitTime) {
		this.visitTime = visitTime;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_HOT_PAPER_ID")
	@SequenceGenerator(name = "T_HOT_PAPER_ID", sequenceName = "T_HOT_PAPER_ID", allocationSize=20)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	


}