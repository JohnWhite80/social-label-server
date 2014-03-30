package com.sociallabel.server.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sociallabel.server.APIException;
import com.sociallabel.server.entity.User;
import com.sociallabel.server.util.SecurityUtil;

@Service("userService")
public class UserService {
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void addUser(User u) {
		if (u.getEmail() == null || u.getUsername() == null || u.getUserpwd() == null) {
			throw new APIException(400, "bad request");
		}
		TypedQuery<User> q = em.createQuery(
				"SELECT u FROM User u where u.email = ?", User.class);
		q.setParameter(1, u.getEmail());
		if (q.getResultList().size() > 0) {
			throw new APIException(400, "duplicated login name");
		}		
		//Encrypt password for security
		u.setUserpwd(SecurityUtil.encrypt(u.getUserpwd()));
		//u.setUsername(SecurityUtil.encrypt(u.getUsername()));
		em.persist(u);
	}

	public List<User> findAllUsers() {
		return em.createQuery("SELECT u FROM User u", User.class)
				.getResultList();
	}
	
	
	public void updateUser(User u) {
		User user = em.find(User.class, u.getEmail());
		user.setBirthday(u.getBirthday());
		user.setCity(u.getCity());
		user.setPicture(u.getPicture());
		user.setSex(u.getSex());
		em.persist(user);
	}
}
