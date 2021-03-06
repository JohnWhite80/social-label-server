package com.sociallabel.server.action;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TagSupport;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.sociallabel.server.APIException;
import com.sociallabel.server.entity.User;
import com.sociallabel.server.entity.UserTag;
import com.sociallabel.server.service.UserService;
import com.sociallabel.server.util.SecurityUtil;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

	private static final Logger logger = LoggerFactory.getLogger(APIController.class);
	@Autowired
	private UserService userService;
	protected final RestTemplate template = new RestTemplate();
	
	int i=0;
    int j=0;
	User user=new User();
	
	@RequestMapping (value = "/users")
    public @ResponseBody List<User> findAllUsers() {
         return userService.findAllUsers();
    }

	
	
	
	
	//完善个人信息的操作
	@RequestMapping(value = "/uploadinformation")
	@ResponseBody
	
	public String upload(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		StringBuffer sb = new StringBuffer("");
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			//打印android端上传的JSON数据
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		String pictureString=jsonObject.getString("picture");
		//String email=jsonObject.getString("email");
		FileWriter fw = new FileWriter("F:\\A.txt");
		fw.write(pictureString); 
		
		String picture="F:\\A.txt";
		String sex=jsonObject.getString("sex");
		String birthday=jsonObject.getString("birthday");
		String city=jsonObject.getString("city");
		
		User u=new User();
		u.setEmail("123456789@qq.com");
		u.setPicture(picture);
		u.setSex(sex);
		u.setBirthday(birthday);
		u.setCity(city);
		userService.updateUser(u);
		
		PrintWriter pw = response.getWriter();
		//封装服务器返回的JSON对象
		JSONObject jsonReply = new JSONObject();
		jsonReply.put("completInformation","upload success");
		//打印返回的JSON数据
		System.out.println(jsonReply);
		pw.write(jsonReply.toString());
		pw.flush();
		pw.close();	
		return null;			
	}
	
	//注册操作
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public String register(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{		
		StringBuffer sb = new StringBuffer("");
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			//打印android端上传的JSON数据
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		String email = jsonObject.getString("e_mail");
		String password=jsonObject.getString("password");
		String username=jsonObject.getString("nikeName");
		user.setEmail(email);
		user.setUsername(username);
		user.setUserpwd(password);
		
		List<User> list=userService.findAllUsers();
		for(;i<list.size();i++){
			if(!(email.equals(list.get(i).getEmail()))){
				j++;
			}else{
				break;
			}
		}
		System.out.println(j);
		if(String.valueOf(list.size()).equals(String.valueOf(j))){
			//userService.addUser(user);
//			String email=request.getParameter("email");
//			String username=request.getParameter("username");
//			String password=request.getParameter("password");
//			user.setEmail(email);
//			user.setUsername(username);
//			user.setUserpwd(password);
			//HttpEntity<User> requestEntity = new HttpEntity(user);
			//ResponseEntity<String> result1 = template.postForEntity("http://localhost:8080/server/api/register1", user, String.class);
			userService.addUser(user);
			System.out.println("Register Success");
			PrintWriter pw = response.getWriter();
			//封装服务器返回的JSON对象
			JSONObject jsonReply = new JSONObject();
			jsonReply.put("register","register success");
			jsonReply.put("email",user.getEmail());
			//打印返回的JSON数据
			System.out.println(jsonReply);
			pw.write(jsonReply.toString());
			pw.flush();
			pw.close();
		}else{
			System.out.println("Register Error");
			PrintWriter pw = response.getWriter();
			//封装服务器返回的JSON对象
			JSONObject jsonReply = new JSONObject();
			jsonReply.put("register","register error");
			//打印返回的JSON数据
			System.out.println(jsonReply);
			pw.write(jsonReply.toString());
			pw.flush();
			pw.close();
			
		}
		
	return "";
		
	}
	//插入标签
	@RequestMapping(value = "/addtag")
	@ResponseBody
	public String addTag(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		StringBuffer sb = new StringBuffer("");
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			//打印android端上传的JSON数据
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		
		String tag1=jsonObject.getString("tag1");
		String tag2=jsonObject.getString("tag2");
		String tag3=jsonObject.getString("tag3");
		String tag4=jsonObject.getString("tag4");
		String tag5=jsonObject.getString("tag5");
		
		//should be replaced by spring controller
		User u = new User();
		u.setEmail("123456@163.com");
		
		Set<UserTag> userTags = new HashSet<UserTag>();
		UserTag tag = new UserTag();
		tag.setName(tag1);
		userTags.add(tag);
		
		tag = new UserTag();
		tag.setName(tag2);
		userTags.add(tag);
		
		tag = new UserTag();
		tag.setName(tag3);
		userTags.add(tag);
		
		tag = new UserTag();
		tag.setName(tag4);
		userTags.add(tag);
		
		tag = new UserTag();
		tag.setName(tag5);
		userTags.add(tag);

		u.setUserTags(userTags);
		//HttpEntity<UserTag> requestEntity = new HttpEntity(tag);
		//ResponseEntity<String> result1 = template.postForEntity("http://localhost:8080/server/api/addtag", tag, String.class);
		userService.addtag(u);
		
		//封装服务器返回的JSON对象
		JSONObject jsonReply = new JSONObject();
		jsonReply.put("addtag","add success");
		return jsonReply.toString();
	}
	//登录操作
	@RequestMapping(value = "/logging")
	@ResponseBody
	public String loggingUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		StringBuffer sb = new StringBuffer("");
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			//打印android端上传的JSON数据
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		String email = jsonObject.getString("name");
		String password=jsonObject.getString("password");
		
		User user=new User();
		user.setEmail(email);
		user.setUserpwd(password);
		List<User> list=userService.findAllUsers();
		for(;i<list.size();i++){	
			if(!(email.equals(list.get(i).getEmail())&&SecurityUtil.encrypt(password).equals(list.get(i).getUserpwd()))){
				j++;
			}else{
				break;
			}
		}
		System.out.println(j);
		
		if(String.valueOf(list.size()).equals(String.valueOf(j))){
			System.out.println("Login Error");
			PrintWriter pw = response.getWriter();
			//封装服务器返回的JSON对象
			JSONObject jsonReply = new JSONObject();
			jsonReply.put("login","login error");
			//打印返回的JSON数据
			System.out.println(jsonReply);
			pw.write(jsonReply.toString());
			pw.flush();
			pw.close();
			
		}else{
			System.out.println("Login Success");
			PrintWriter pw = response.getWriter();
			//封装服务器返回的JSON对象
			JSONObject jsonReply = new JSONObject();
			jsonReply.put("login","login success");
			//打印返回的JSON数据
			System.out.println(jsonReply);
			pw.write(jsonReply.toString());
			pw.flush();
			pw.close();
			
		}
		return "";
		
	}	
	@RequestMapping(value = "/search")
	@ResponseBody
	public JSONObject search(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {
		
		StringBuffer sb = new StringBuffer("");
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "utf-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			//打印android端上传的JSON数据
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		String tagname=jsonObject.getString("tagname");
//		System.out.println(tagname);
		 //String tagname=request.getParameter("tagname");
		 List userlist=new LinkedList();
		 List<Map<String,Object>> totallist=new ArrayList<Map<String,Object>>();
		 List<UserTag> similartagslist=userService.findByTagContaining(tagname);
		 for(int i=0;i<similartagslist.size();i++){	 
			String name=similartagslist.get(i).getName();
		    System.out.println(name);
		    userlist=userService.searchTagGetUser(similartagslist.get(i).getName());
		   		    
	        Map<String,Object> map=new HashMap<String,Object>();
	        map.put("tagname",name );
	        map.put("users", userlist);
	        totallist.add(map);
		 }
		 System.out.println(totallist);
		 JSONObject jsonReply = new JSONObject();
		jsonReply.put("searchResult",totallist);
		
		return jsonReply;
		
	}

	@RequestMapping(value = "/searchTagGetUser")
	@ResponseBody
	public List searchTagGetUser(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		        
	           String name=request.getParameter("tagname");
	           List usernamelist=new LinkedList();
	         List<UserTag> list = userService.findByName(name);
	         Set<User> users=new HashSet<User>();
	              for(int i=0;i<list.size();i++){  	            	 
	            	  users=list.get(i).getUsers();
	            	  Iterator it=users.iterator();//将hs转换为一个可遍历的对象Iterator
	            	  while(it.hasNext()){
	            		  User user=(User)it.next();
	            		  String username=user.getUsername();
	                      usernamelist.add(username);
	            		  System.out.println(username);
	            	  }
	              }
	             
	           return usernamelist;
	}
	
	@RequestMapping(value = "/searchUserGetTag")
	@ResponseBody
	public String searchUserGetTag(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
	           String username=request.getParameter("username");
	         List<User> list = userService.findByUsername(username);
	         Set<UserTag> usertags=new HashSet<UserTag>();
	              for(int i=0;i<list.size();i++){            	
	            	  usertags=list.get(i).getUserTags();
	            	  Iterator tit=usertags.iterator();//将hs转换为一个可遍历的对象Iterator
	            	  while(tit.hasNext()){
	            		  UserTag usertag=(UserTag)tit.next();
	            		  String tagname=usertag.getName();
	            		  System.out.println(tagname);
	            	  }
	              }
	             
	           return username;
	}
	

	//搜索操作
	@RequestMapping(value = "/searchSimilarTag")
	@ResponseBody
	public String searchSimilarTag(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		StringBuffer sb = new StringBuffer("");
//		String result = "";
//		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					request.getInputStream(), "utf-8"));
//			String temp;
//			while ((temp = br.readLine()) != null) {
//				sb.append(temp);
//			}
//			br.close();
//			result = sb.toString();
//			//打印android端上传的JSON数据
//			System.out.println(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JSONObject jsonObject = JSONObject.fromObject(result);
//		String name=jsonObject.getString("name");
		String nametag=request.getParameter("tagname");
		List<UserTag> list=userService.findByTagContaining(nametag);
//		List<UserTag> list=userService.findByTagContainingOrderByTagDESC(nametag);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getName()); 
		}
//		JSONObject jsonReply = new JSONObject();
//		if(tag!=null){
//			for(i=0;i<tag.size();i++){
//				String sr=tag.get(i).getName();
//				jsonReply.put("sr", sr);
//				jsonReply.put("ss", "searchsuccess");
//			}
//			System.out.println("search success");
//			PrintWriter pw = response.getWriter();
//			System.out.println(jsonReply);
//			pw.write(jsonReply.toString());
//			pw.flush();
//			pw.close();
//			
//		}
//		else{
//			System.out.println("Search Error");
//			PrintWriter pw = response.getWriter();
//			//封装服务器返回的JSON对象
//			JSONObject jsonReply1 = new JSONObject();
//			jsonReply1.put("se","search error");
//			//打印返回的JSON数据
//			System.out.println(jsonReply1);
//			pw.write(jsonReply1.toString());
//			pw.flush();
//			pw.close();
//		}
		return nametag;
	}
//推荐操作
@RequestMapping(value = "/recommend")
	@ResponseBody
	public String recommend(HttpServletRequest request,HttpServletResponse response){
		String email=request.getParameter("email");
		User u=new User();
		u.setEmail(email);
		List userlist=new LinkedList();
		 List<Map<String,Object>> totallist=new ArrayList<Map<String,Object>>();
		List<UserTag> t=userService.recommend(u);
		for(i=0;i<=t.size();i++){
			String tagname=t.get(i).getName();
			System.out.println(t.get(i).getName());
			userlist=userService.searchTagGetUser(tagname);
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("tagname", tagname);
			 map.put("users", userlist);
			 totallist.add(map);
			 //System.out.println(totallist);
		}
		System.out.println(totallist);
		return "";
		
	}
//接收tigase信息
@RequestMapping(value = "/create",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<String> receive(@RequestParam(value="room_address") String room_address,@RequestParam(value="room_creator") String creator){
	
	String creator_name=creator.replaceAll("@192.168.191.1", "");
	System.out.println(room_address);
	System.out.println(creator_name);
	//System.out.println(map.get("creator"));
	MultiValueMap<String, String> map1=new LinkedMultiValueMap<String, String>();
	return new ResponseEntity<String>("", map1, HttpStatus.OK);
	
}
//
@RequestMapping(value = "/destroy/{map}",method = RequestMethod.GET)
@ResponseBody
public ResponseEntity<String> receive1(@PathVariable("map") MultiValueMap<String, Integer> map){
	
	System.out.println(map);
	MultiValueMap<String, String> map1=new LinkedMultiValueMap<String, String>();
	return new ResponseEntity<String>("", map1, HttpStatus.OK);
	
}



	@RequestMapping(value = "/register1")
	@ResponseBody
	public JSONObject registerUser(@RequestBody User user, Model model) {
		userService.addUser(user);
		JSONObject jsonReply = new JSONObject();
		jsonReply.put("register","register success");
		jsonReply.put("email",user.getEmail());
		return jsonReply;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> profile(@RequestParam("userId") String useremail, @RequestParam("filename") String filename, @RequestPart("image") MultipartFile file,@RequestParam("birthday") String birthday,@RequestParam("sex") String sex,@RequestParam("city") String city) throws Exception {
		userService.updateProfile(useremail, filename, file);
		User u=new User();
		u.setSex(sex);
		u.setBirthday(birthday);
		u.setCity(city);
		userService.updateUser(u);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-code", "200");
		responseHeaders.set("x-message", "success");
		return new ResponseEntity<String>("", responseHeaders, HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		int errorCode = 500;
		String errorMessage = e.getMessage();
		if(e instanceof APIException) {
			errorCode = ((APIException)e).getErrorCode();
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-code", String.valueOf(errorCode));
		responseHeaders.set("x-message", errorMessage);
		return new ResponseEntity<String>("", responseHeaders, HttpStatus.OK);
	}

}
