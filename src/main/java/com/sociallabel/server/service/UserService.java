package com.sociallabel.server.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sociallabel.server.APIException;
import com.sociallabel.server.entity.User;
import com.sociallabel.server.entity.UserTag;
import com.sociallabel.server.repository.TagRepository;
import com.sociallabel.server.repository.UserRepository;
import com.sociallabel.server.util.SecurityUtil;

@Service("userService")
public class UserService {
	
	private @Value("${app.image.store.path}") String path;
	
	@Autowired
	private UserRepository userRepository;
	private TagRepository tagRepository;
	@Transactional
	public User addUser(User u) {
		if (u.getEmail() == null || u.getUsername() == null || u.getUserpwd() == null) {
			throw new APIException(400, "bad request");
		}
		List<User> users = userRepository.findByEmail(u.getEmail());
		List<UserTag> tag=tagRepository.findByEmail(u.getEmail());
		if (users.size() > 0) {
			throw new APIException(400, "duplicated login name");
		}		
		//Encrypt password for security
		u.setUserpwd(SecurityUtil.encrypt(u.getUserpwd()));
		tagRepository.save(tag);
		return userRepository.saveAndFlush(u);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	@Transactional
	public void updateProfile(long userId, String filename, MultipartFile file) throws Exception {
		//MediaType mediaType = MediaType.parseMediaType(file.getContentType());	
		String ext = filename.substring(filename.indexOf(".")+1, filename.length());
		File f = new File(path + File.separator + System.currentTimeMillis() + "." + ext);
		file.transferTo(f);
		User user = userRepository.findOne(userId);
		user.setPicture(f.getAbsolutePath());
		userRepository.saveAndFlush(user);
	}
	@Transactional
	public UserTag addtag(UserTag t){
		//List<UserTag> tag=tagRepository.findByEmail(t.getEmail());
		t.setTag1(t.getTag1());
		t.setTag2(t.getTag2());
		t.setTag3(t.getTag3());
		t.setTag4(t.getTag4());
		t.setTag5(t.getTag5());
		return tagRepository.saveAndFlush(t);
		
			
	}
	@Transactional
	public void updateUser(User u) {
		User user=userRepository.findOne(u.getId());
		user.setSex(u.getSex());
		user.setBirthday(u.getBirthday());
		user.setCity(u.getCity());
		userRepository.saveAndFlush(user);
//		TypedQuery<User> q = em.createQuery(
//				"SELECT u FROM User u where u.email = ?", User.class);
//		q.setParameter(1, u.getEmail());
//		List<User> list = q.getResultList();
//		if(list.isEmpty()) {
//			throw new APIException(400, "user not exist");
//		}
//		User user = list.get(0);
//		System.out.println(user.getEmail());
//		user.setBirthday(u.getBirthday());
//		user.setCity(u.getCity());
//		user.setPicture(u.getPicture());
//		user.setSex(u.getSex());
//		em.persist(user);
	   //em.joinTransaction();
		
	}
}
