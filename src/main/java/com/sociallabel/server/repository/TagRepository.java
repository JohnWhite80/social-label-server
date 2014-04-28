package com.sociallabel.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.sociallabel.server.entity.UserTag;

public interface TagRepository extends JpaRepository<UserTag, String> {
	List<UserTag> findByName(String name);
}
