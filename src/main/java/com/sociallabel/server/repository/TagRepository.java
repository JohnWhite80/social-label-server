package com.sociallabel.server.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import com.sociallabel.server.entity.UserTag;

public interface TagRepository extends JpaRepository<UserTag, String> {
	
	List<UserTag> findByName(String name);

	 @Query(" from UserTag t where t.name like %?1% ")
	List<UserTag> findByTagContaining(String nameTag);
}
//	@SuppressWarnings("rawtypes")
//	List<UserTag> findByUsers(Set users);

