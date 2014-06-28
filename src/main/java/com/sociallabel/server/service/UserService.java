package com.sociallabel.server.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private EntityManager em;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TagRepository tagRepository;
	
	@Transactional
	public User addUser(User u) {
		if (u.getEmail() == null || u.getUsername() == null || u.getUserpwd() == null) {
			throw new APIException(400, "bad request");
		}
		List<User> users = userRepository.findByEmail(u.getEmail());
		if (users.size() > 0) {
			throw new APIException(400, "duplicated login name");
		}		
		//Encrypt password for security
		u.setUserpwd(SecurityUtil.encrypt(u.getUserpwd()));	
		return userRepository.saveAndFlush(u);
				
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	public List<User> findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	public List<UserTag> findByName(String name){
		return tagRepository.findByName(name);
	}
	 public List<UserTag> findByTagContaining(String nameTag){
		 return tagRepository.findByTagContaining(nameTag);
	 }
	 
//	 @SuppressWarnings("rawtypes")
//	public List<User> findByUserTags(Set UserTags){
//		 return userRepository.findByUserTags(UserTags);
//	 }
//	
//	 
//	 @SuppressWarnings("rawtypes")
//		public List<UserTag> findByUsers(Set users){
//		 return tagRepository.findByUsers(users);
//	 }
	 

	@Transactional
	public void updateProfile(String email, String filename, MultipartFile file) throws Exception {
		String ext = filename.substring(filename.indexOf(".")+1, filename.length());
		File f = new File(path + File.separator + System.currentTimeMillis() + "." + ext);
		file.transferTo(f);
		User user = (User)userRepository.findByEmail(email);
		user.setPicture(f.getAbsolutePath());
		userRepository.saveAndFlush(user);
	}
	@Transactional
	public void addtag(User u){
		List<User> users = userRepository.findByEmail(u.getEmail());
		if (users.size() == 0) {
			throw new APIException(400, "user not exist");
		}
		User user = users.get(0);
		Set<UserTag> tags = u.getUserTags();
		Set<UserTag> userTags = new HashSet<UserTag>();
		if(userTags != null) {
			for(UserTag t:tags) {
				List<UserTag> tag = tagRepository.findByName(t.getName());
				if(tag.isEmpty()) {
					userTags.add(tagRepository.save(t));
				} else {
					userTags.add(tag.get(0));
				}
				
			}
		}
		user.setUserTags(userTags);
		userRepository.save(user);
	}
	
	@Transactional
	public void updateUser(User u) {
		User user=(User)userRepository.findByEmail(u.getEmail());
		user.setSex(u.getSex());
		user.setBirthday(u.getBirthday());
		user.setCity(u.getCity());
		userRepository.saveAndFlush(user);
		
	}
	
	@Transactional
	public List<UserTag> searchTag(UserTag t){
		String name=t.getName();
		TypedQuery<UserTag> q = em.createQuery(
				"SELECT t FROM UserTag t where t.Name like '%"+name+"%' ", UserTag.class);
		List<UserTag> list=q.getResultList();
		
		return list;
	}
	
	
	public List searchTagGetUser(String tagname){
        
        List usernamelist=new ArrayList();
      List<UserTag> list = tagRepository.findByName(tagname);
      Set<User> users=new HashSet<User>();
           for(int i=0;i<list.size();i++){  	            	 
         	  users=list.get(i).getUsers();
         	  Iterator it=users.iterator();//将hs转换为一个可遍历的对象Iterator
         	  while(it.hasNext()){
         		  User user=(User)it.next();
         		  //String username=user.getUsername();
         		  Map<String, String> map=new HashMap<String, String>();
         		  map.put("image", user.getPicture());
         		  map.put("nikename", user.getUsername());
         		 // map.put("email", user.getEmail());
         		  map.put("grade","123");
                  usernamelist.add(map);
                   
         		  //System.out.println(username);
         	  }
           }
          
        return usernamelist;
}
	@Transactional
	public List<UserTag> recommend(User u){
		List<User> t=userRepository.findByEmail(u.getEmail());
		Set<UserTag> tag=t.get(0).getUserTags();
		List<UserTag> rtback = new ArrayList<UserTag>();
		 Iterator<UserTag> it = tag.iterator();
		 while(it.hasNext()){
			 UserTag rt=it.next();
			 //String rtname=rt.getName();
			 rtback.add(rt);
		 }
		return rtback;
	}
	
	

}
