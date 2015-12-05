package com.bjut.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TInstitution entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_institution")
public class Institution extends IdEntity {

	// Fields

	
	private String name;
	private String type;
	private String nature;
	private String custmode;
	private String extenone;
	private String extentwo;

	// Constructors

	/** default constructor */
	public Institution() {
	}

	
	public Institution(Long id) {
		this.id = id;
	}
	/** full constructor */
	public Institution(String name, String type, String nature,
			String custmode, String extenone, String extentwo) {
		this.name = name;
		this.type = type;
		this.nature = nature;
		this.custmode = custmode;
		this.extenone = extenone;
		this.extentwo = extentwo;
	}

	// Property accessors

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNature() {
		return this.nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getCustmode() {
		return this.custmode;
	}

	public void setCustmode(String custmode) {
		this.custmode = custmode;
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

}