package com.example.imgurProject.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.imgurProject.VO.JwtResponse;
import com.example.imgurProject.VO.MemberResponseVO;
import com.example.imgurProject.VO.MemberVO;
import com.example.imgurProject.exception.CustomException;
import com.example.imgurProject.model.JwtUtil;
import com.example.imgurProject.model.Member;
import com.example.imgurProject.repo.MemberRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl{
	 @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private MemberRepo userRepository;

	    @Autowired
	    private JwtUtil jwtUtil;
	    public MemberResponseVO createUser(MemberVO user) {
           log.info("Checking whether username already exist or not");
           String username = user.getUserName();
           log.debug("Username provided: {}", username);
           Member existingUser = userRepository.findByUserName(username);
           if (existingUser!=null) {
              log.warn("User with username '{}' already exists", username);
              throw new CustomException("User already exists", "USER_EXISTS");
           }
           String encrypted_Password = passwordEncoder.encode(user.getPassword());
           // Create a new UserEntity from the UserRequest
           Member userEntity = Member.builder()
                .userName(user.getUserName())
                .password(encrypted_Password)
                .emailId(user.getEmailId())
                .gender(user.getGender())
                .age(user.getAge())
                .build();
          // Save the new user entity to the database
          userEntity = userRepository.save(userEntity);
          // Created a new UserResponse and copied data from the saved UserEntity
          MemberResponseVO userResponse = new MemberResponseVO();
          BeanUtils.copyProperties(userEntity, userResponse);
          return userResponse;
        }
	     public JwtResponse authorizeUser(String username, String password) {
	        try {
	            MemberResponseVO user = getUserByName(username);
	            if (user!=null) {
	                MemberResponseVO userDetails = user;
	                if (passwordEncoder.matches(password, userDetails.getPassword())) {
	                    final String jwt = jwtUtil.generateToken(userDetails.getUserName());
	                    return new JwtResponse(jwt);
	                } else {
	                    throw new CustomException("Invalid username/password", "BAD_CREDENTIALS");
	                }
	            } else {
	                throw new CustomException("User doesn't exist", "USER_NOT_EXIST");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new CustomException("An error occurred during authorization.", "AUTH_ERROR");
	        }
	    }
	     public MemberResponseVO getUserByName(String userName) {
	         Member userEntity = userRepository.findByUserName(userName);
	         MemberResponseVO userResponse = new MemberResponseVO();
	         BeanUtils.copyProperties(userEntity, userResponse);
	         return userResponse;
	     }
}
