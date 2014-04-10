package com.sociallabel.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="t_Tag")
public class UserTag {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;

	// ” œ‰
	@Id
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	// ±Í«©
	@Column(length = 256)
	private String tag1 = "";
	@Column(length = 256)
	private String tag2 = "";
	@Column(length = 256)
	private String tag3 = "";
	@Column(length = 256)
	private String tag4 = "";
	@Column(length = 256)
	private String tag5 = "";

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTag1() {
		return tag1;
	}

	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}

	public String getTag2() {
		return tag2;
	}

	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}

	public String getTag3() {
		return tag3;
	}

	public void setTag3(String tag3) {
		this.tag3 = tag3;
	}

	public String getTag4() {
		return tag4;
	}

	public void setTag4(String tag4) {
		this.tag4 = tag4;
	}

	public String getTag5() {
		return tag5;
	}

	public void setTag5(String tag5) {
		this.tag5 = tag5;
	}

}
