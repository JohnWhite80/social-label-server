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
@Table(name="T_SUBJECT")
public class RoomSubject {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//房间主题
	@Column(length = 128, nullable = false, unique = true)
	private String subject;

	@ManyToMany(cascade = CascadeType.REFRESH,fetch=FetchType.EAGER)

	@JoinTable(name = "T_ROOM_SUBJECT", inverseJoinColumns = @JoinColumn(name = "ROOM_ID",referencedColumnName="ID"), joinColumns = @JoinColumn(name = "SUBJECT_ID",referencedColumnName="ID"))  
	private Set<UserTag> userTags = new HashSet<UserTag>(); 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
