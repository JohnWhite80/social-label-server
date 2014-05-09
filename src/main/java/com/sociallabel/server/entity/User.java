package com.sociallabel.server.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_USER")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//邮箱
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	//用户名
	@Column(length = 128, nullable = false)
	private String username;
	//密码
	@Column(length = 256, nullable = false)
	private String userpwd;
	//头像
	@Column(length = 256)
	private String picture=" ";
	//性别
	@Column(length = 256)
	private String sex=" ";
	//生日
	@Column(length = 256)
	private String birthday=" ";
	//地区
	@Column(length = 256)
	private String city=" ";
	

	@ManyToMany(cascade = CascadeType.REFRESH,fetch=FetchType.EAGER)

	@JoinTable(name = "T_USER_TAG", inverseJoinColumns = @JoinColumn(name = "TAG_ID",referencedColumnName="ID"), joinColumns = @JoinColumn(name = "USER_ID",referencedColumnName="ID"))  
	private Set<UserTag> userTags = new HashSet<UserTag>(); 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Set<UserTag> getUserTags() {
		return userTags;
	}
	public void setUserTags(Set<UserTag> userTags) {
		this.userTags = userTags;
	}	
	
}
