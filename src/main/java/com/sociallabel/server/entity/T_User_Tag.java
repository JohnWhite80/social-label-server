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
@Table(name="T_USER_TAG")
public class T_User_Tag {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	//房间人数
	@Column(length = 128, nullable = false, unique = false)
	private String num_people;
	//房间状态
	@Column(length = 128, nullable = false, unique = false)
	private String Room_Status;
	//@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="t_user_tag",fetch=FetchType.EAGER)
	//private Set<User> users=new HashSet<User>();
	@Column(length = 128, nullable = false, unique = false)
	private String Room_Address;
	public String getRoom_Address() {
		return Room_Address;
	}
	public void setRoom_Address(String room_Address) {
		Room_Address = room_Address;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNum_people() {
		return num_people;
	}
	public void setNum_people(String num_people) {
		this.num_people = num_people;
	}
	public String getRoom_Status() {
		return Room_Status;
	}
	public void setRoom_Status(String room_Status) {
		Room_Status = room_Status;
	}
	
}
