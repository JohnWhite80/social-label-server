package com.sociallabel.server.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sociallabel.server.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByEmail(String email);
	List<User> findByUsername(String username);

//	@SuppressWarnings("rawtypes")
//	List<User> findByUserTags(Set UserTags);
//	

	
}
