package com.sociallabel.server.action;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class APIControllerTest {
	
	protected final RestTemplate template = new RestTemplate();
	
//	@Test
//	public void testRegisterUser() {
//		User u = new User();
//		//u.setLoginName("loginname2");
//		//u.setName("name2");
//		//u.setPassword("password2");
//		UserService userService = null;
//		u.setEmail("2217282890@qq.com");
//		u.setBirthday("123");
//		u.setCity("123");
//		u.setPicture("123");
//		u.setSex("123");
//		//userService.addUser(u);
//	}
	@Test
	public void testaddtag(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("tag1", "a");
		map.put("tag2", "d");
		map.put("tag3", "c");
		map.put("tag4", "d");
		map.put("tag5", "e");
		ResponseEntity<String> result = template.postForEntity("http://localhost:8080/server/api/addtag", map, String.class);
		System.out.println(result);
		HttpStatus statusCode = result.getStatusCode();
		String code = result.getHeaders().get("x-code").get(0);
		String message = result.getHeaders().get("x-message").get(0);
		assertEquals(HttpStatus.OK, statusCode);
		assertEquals(code, "200");
		assertEquals(message, "success");
	}
	
}	//HttpEntity<User> requestEntity = new HttpEntity(u);
		//ResponseEntity<String> result = template.postForEntity("http://localhost:8080/server/api/register", u, String.class);
//		System.out.println(result);
//		HttpStatus statusCode = result.getStatusCode();
//		String code = result.getHeaders().get("x-code").get(0);
//		String message = result.getHeaders().get("x-message").get(0);
//		assertEquals(HttpStatus.OK, statusCode);
//		assertEquals(code, "200");
//		assertEquals(message, "success");
	
//	@Test
//	public void testProfile() {
//		
//		
//		
//		
//		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
//		parts.add("userId", 1);
//		parts.add("image", new FileSystemResource("C:\\Users\\Administrator.supervisor\\Desktop\\social-label-server\\social-label-server\\456118.jpg"));
//
//		ResponseEntity<String> result = template.postForEntity("http://10.247.64.239:8080/server/api/profile", parts, String.class);
//		
//		System.out.println(result);
//		HttpStatus statusCode = result.getStatusCode();
//		String code = result.getHeaders().get("x-code").get(0);
//		String message = result.getHeaders().get("x-message").get(0);
//		assertEquals(HttpStatus.OK, statusCode);
//		assertEquals(code, "200");
//		assertEquals(message, "success");
//	}
//	
//	@Test 
//	public void testA(){
//		String filename ="a.jpg";
//		String ext = filename .substring(filename.indexOf(".")+1, filename.length());
//		System.out.println(ext);
//	}
//}



