package com.sociallabel.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sociallabel.server.entity.T_User_Tag;

public interface T_User_TagRepository extends JpaRepository<T_User_Tag, String> {
	

}
