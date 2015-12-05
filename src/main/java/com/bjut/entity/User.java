package com.bjut.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import com.bjut.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;


@Entity
@Table(name = "t_user")
public class User extends IdEntity {

	// Fields

	private String loginName;
	private String name;
	private String plainPassword;
	private String password;
	private String salt;
	private String roles;
	private String realname;
	private String affiname;
	private String perm;
	private String major;
	private String extensionone;
	private String extensiontwo;
    private String title;//职称
    private String emails;//邮箱
    private String visit;//访问次数 
    private String userNo;//学号
	private Date registerDate;
	// Constructors

	/** default constructor */
	public User() {
	}
    public User(Long id){
    	this.id  = id;
    }
	/** full constructor */
	

	// Property accessors



	public String getPassword() {
		return this.password;
	}

	public User(String loginName, String name, String plainPassword,
			String password, String salt, String roles, String loginname2,
			String realname, String affiname, String perm, String major,
			String extensionone, String extensiontwo, String title,
			String emails, String visit, String userNo) {
		super();
		this.loginName = loginName;
		this.name = name;
		this.plainPassword = plainPassword;
		this.password = password;
		this.salt = salt;
		this.roles = roles;
	
		this.realname = realname;
		this.affiname = affiname;
		this.perm = perm;
		this.major = major;
		this.extensionone = extensionone;
		this.extensiontwo = extensiontwo;
		this.title = title;
		this.emails = emails;
		this.visit = visit;
		this.userNo = userNo;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAffiname() {
		return this.affiname;
	}

	public void setAffiname(String affiname) {
		this.affiname = affiname;
	}

	public String getPerm() {
		return this.perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getExtensionone() {
		return this.extensionone;
	}

	public void setExtensionone(String extensionone) {
		this.extensionone = extensionone;
	}

	public String getExtensiontwo() {
		return this.extensiontwo;
	}

	public void setExtensiontwo(String extensiontwo) {
		this.extensiontwo = extensiontwo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getVisit() {
		return visit;
	}

	public void setVisit(String visit) {
		this.visit = visit;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	// 不持久化到数据库，也不显示在Restful接口的属性.
		@Transient
		@JsonIgnore
		public String getPlainPassword() {
			return plainPassword;
		}
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Transient
	@JsonIgnore
	public List<String> getRoleList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		return ImmutableList.copyOf(StringUtils.split(roles, ","));
	}
	// 设定JSON序列化时的日期格式
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
		public Date getRegisterDate() {
			return registerDate;
		}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	

}