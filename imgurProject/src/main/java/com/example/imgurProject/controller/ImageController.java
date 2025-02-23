package com.example.imgurProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.imgurProject.VO.ImageDetailsVO;
import com.example.imgurProject.VO.MemberProfileVO;
import com.example.imgurProject.model.JwtUtil;
import com.example.imgurProject.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@RestController()
@RequestMapping("/image")
@Slf4j
public class ImageController {
     @Autowired
     private ImageService imageService;
     @Autowired
     private JwtUtil jwtUtil;
     
     @PostMapping("/uploadImage")
     public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authToken)
     {
    	 String userName=null;
    	 try {
    		 if (authToken.startsWith("Bearer ")) {
                 authToken = authToken.substring(7);
    		 }
    		 validateToken(authToken);
    		 userName = jwtUtil.getUsernameFromToken(authToken);
    		 log.info("Username extracted from token:{}",userName);
    		 imageService.uploadImage(file,userName);
    		 log.info("Image uploaded successfully for user: {}", userName);
    	     return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
    	 }catch(Exception e)
    	 {
    		 if (userName != null) {
                 log.error("Error while uploading the image for user: {}", userName, e);
             } else {
                 log.error("Error while validating token or extracting username.", e);
             }
    		 return new ResponseEntity<>("Image upload failed",HttpStatus.INTERNAL_SERVER_ERROR);
    	 }
     }
     @DeleteMapping("/deleteImage/{imageId}")
     public ResponseEntity<String> deleteImageForAuthenticatedUser(@PathVariable String imageId, @RequestHeader("Authorization") String authToken) {
         try {
             if (authToken.startsWith("Bearer ")) {
            	 authToken = authToken.substring(7);
             }
             validateToken(authToken);
             log.info("validated token");

             //Deleting the image with ImageId
             imageService.deleteImage(imageId);
             log.info("Image deleted successfully for user: {}", imageId);
             return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
         } catch (Exception e) {
             log.error("Error while deleting the image", e);
             return new ResponseEntity<>("Image delete failed",HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }
     @GetMapping("/viewAllImages")
     public List<ImageDetailsVO> viewAllImagesOfUser(@RequestHeader("Authorization") String authToken)
     {
    	 String userName=null;
    	 try {
    		 if (authToken.startsWith("Bearer ")) {
                 authToken = authToken.substring(7);
    		 }
    		 validateToken(authToken);
    		 userName = jwtUtil.getUsernameFromToken(authToken);
    		 log.info("Username extracted from token:{}",userName);
    	     return imageService.getAllImages(userName);
    	 }catch(Exception e)
    	 {
             log.error("Error while validating token or extracting username.", e);
    		 return null;
    	 }
     }
     @GetMapping("/viewUserProfile")
     public MemberProfileVO viewUserProfileWithImages(@RequestHeader("Authorization") String authToken)
     {
    	 String userName=null;
    	 try {
    		 if (authToken.startsWith("Bearer ")) {
                 authToken = authToken.substring(7);
    		 }
    		 validateToken(authToken);
    		 userName = jwtUtil.getUsernameFromToken(authToken);
    		 log.info("Username extracted from token:{}",userName);
    	     return imageService.getUserProfileWithImages(userName);
    	 }catch(Exception e)
    	 {
             log.error("Error while validating token or extracting username.", e);
    		 return null;
    	 }
     }
     private boolean validateToken(String accessToken) {
         try {
             jwtUtil.validateToken(accessToken);
             log.error("Token validation Successfully.");
             return true;
         } catch (Exception e) {
             log.error("Token validation failed.", e);
             throw new RuntimeException("Invalid or expired token.", e);
         }
     }

	
}
