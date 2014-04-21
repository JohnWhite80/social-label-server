package com.sociallabel.server.action;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
			HttpEntity<User> requestEntity = new HttpEntity(user);
			ResponseEntity<String> result1 = template.postForEntity("http://localhost:8080/server/api/register1", user, String.class);
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
		String email=jsonObject.getString("email");
		
		//should be replaced by spring controller
		User u = new User();
		u.setEmail("sz");
		
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
		ResponseEntity<String> result1 = template.postForEntity("http://localhost:8080/server/api/addtag", tag, String.class);
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
	
	
//	@RequestMapping(value = "/register1")
//	@ResponseBody
//	public JSONObject registerUser(@RequestBody User user, Model model) {
//		System.out.println(user.getUserpwd());
//		userService.addUser(user);
//		JSONObject jsonReply = new JSONObject();
//		jsonReply.put("register","register success");
//		jsonReply.put("email",user.getEmail());
//		return jsonReply;
//	}
	
	@RequestMapping(value = "/register1")
	@ResponseBody
	public JSONObject registerUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
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
		String e_mail=jsonObject.getString("e_mail");
		String password=jsonObject.getString("password");
		String nikeName=jsonObject.getString("nikeName");
		User user=new User();
		user.setEmail(e_mail);
		user.setUsername(nikeName);
		user.setUserpwd(nikeName);
		userService.addUser(user);
		JSONObject jsonReply = new JSONObject();
		jsonReply.put("register","register success");
		jsonReply.put("email",user.getEmail());
		return jsonReply;
	}
	
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public JSONObject test(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		System.out.println(name);
		JSONObject jsonReply = new JSONObject();
		jsonReply.put("register",name);
		return jsonReply;
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> profile(@RequestParam("userId") long userId, @RequestParam("filename") String filename, @RequestPart("image") MultipartFile file) throws Exception {
		userService.updateProfile(userId, filename, file);
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

	
	
	@RequestMapping(value = "/registerTest", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> registerTest(@RequestParam("UserId") String UserId, @RequestParam("Password") String Password,@RequestParam("NikeName") String NikeName) throws Exception {
		User user=new User();
		user.setEmail(UserId);
		user.setUsername(NikeName);
		user.setUserpwd(Password);
		userService.addUser(user);
		System.out.println(UserId+"::::"+NikeName+":::"+Password);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-code", "200");
		responseHeaders.set("x-message", "success");
		responseHeaders.set("name", "piterflaskhdfkashdkghsdkjg");
		
		
		return new ResponseEntity<String>("", responseHeaders, HttpStatus.OK);
	}

	

}