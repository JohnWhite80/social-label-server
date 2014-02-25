package com.sociallabel.server.action;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sociallabel.server.entity.User;

public class APIControllerTest {
	
	protected final RestTemplate template = new RestTemplate();
	
	@Test
	public void testRegisterUser() {
		User u = new User();
		u.setLoginName("loginname2");
		u.setName("name2");
		u.setPassword("password2");
		HttpEntity<User> requestEntity = new HttpEntity(u);
		ResponseEntity<String> result = template.postForEntity("http://localhost:8080/server/api/register", u, String.class);
		System.out.println(result);
		HttpStatus statusCode = result.getStatusCode();
		String code = result.getHeaders().get("x-code").get(0);
		String message = result.getHeaders().get("x-message").get(0);
		assertEquals(HttpStatus.OK, statusCode);
		assertEquals(code, "200");
		assertEquals(message, "success");
	}

}
