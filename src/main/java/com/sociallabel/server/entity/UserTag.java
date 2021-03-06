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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_TAG")
public class UserTag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String name;
	
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="userTags",fetch=FetchType.EAGER)
	private Set<User> users = new HashSet<User>();
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="userTags",fetch=FetchType.EAGER)
	private Set<RoomSubject> RoomSubjects = new HashSet<RoomSubject>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	


}
