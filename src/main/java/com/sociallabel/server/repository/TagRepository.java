package com.sociallabel.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sociallabel.server.entity.UserTag;

public interface TagRepository extends JpaRepository<UserTag, Long> {
	List<UserTag> findByEmail(String email);

}
