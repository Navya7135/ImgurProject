package com.example.imgurProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.imgurProject.VO.JwtRequest;
import com.example.imgurProject.VO.JwtResponse;
import com.example.imgurProject.VO.MemberResponseVO;
import com.example.imgurProject.VO.MemberVO;
import com.example.imgurProject.exception.CustomException;
import com.example.imgurProject.service.MemberServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class MemberController {
	    @Autowired
	    private MemberServiceImpl memberService;

	    @PostMapping("/createUser")
	    public ResponseEntity<MemberResponseVO> createUser(@RequestBody MemberVO user) {
	        MemberResponseVO createdUser = memberService.createUser(user);
	        log.info("Successfully saved user : {}", createdUser.getUserName());
	        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	    }
	    @PostMapping("/login")
	    public ResponseEntity<String> authorizeUser(@RequestBody JwtRequest authRequest) {
	    	try {
	        JwtResponse accessToken = memberService.authorizeUser(authRequest.getUserName(), authRequest.getPassword());
	        return new ResponseEntity<>(accessToken.getJwtToken(), HttpStatus.OK);
	    	}
	    	catch(Exception e)
	    	{
	    		log.info("invalid credentials");
	    		return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
	    	}
	    }
	    @GetMapping("/")
	    public String success()
	    {
	    	log.info("Successfully saved user : {}");
	    	return "success";
	    }
}
