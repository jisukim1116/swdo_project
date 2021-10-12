package com.jisu.test.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.jisu.test.service.UserService;
import com.jisu.test.vo.UserVO;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {
	
	
	@Autowired
	private UserService service;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	private static final String CLIENT_ID = "895850026348-73a2do4bd75ujv8rirts59gu3i977ck5.apps.googleusercontent.com";
	
	@RequestMapping(value = "/googleLogin", method = RequestMethod.POST)
	public void googleLogin(@RequestBody String idTokenString) throws GeneralSecurityException, IOException {
		
		String name="";
		String email="";
		UserVO user= new UserVO();
		
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
				
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).setAudience(Collections.singletonList(CLIENT_ID)).build();
		
		GoogleIdToken idToken = verifier.verify(idTokenString);
		
		if (idToken != null) {
		  Payload payload = idToken.getPayload();

		  // Print user identifier
		  String userId = payload.getSubject();
		  System.out.println("User ID: " + userId);

		  // Get profile information from payload
		  email = payload.getEmail();
		  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		  name = (String) payload.get("name");
		  String pictureUrl = (String) payload.get("picture");
		  String locale = (String) payload.get("locale");
		  String familyName = (String) payload.get("family_name");
		  String givenName = (String) payload.get("given_name");
		  
		  System.out.println("User Name: " + name);
		  System.out.println("User Email: " + email);
		  
		  // Use or store profile information
		  // ...

		} else {
		  System.out.println("Invalid ID token.");
		}
		
		user.setUser_id(email);
		user.setUser_name(name);
		
		service.userGoogleLogin(user);

	}
}
