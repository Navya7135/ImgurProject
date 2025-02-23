package com.example.imgurProject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.imgurProject.VO.ImageDetailsVO;
import com.example.imgurProject.VO.ImgurResponseVO;
import com.example.imgurProject.VO.MemberProfileVO;
import com.example.imgurProject.VO.MemberResponseVO;
import com.example.imgurProject.exception.CustomException;
import com.example.imgurProject.model.Image;
import com.example.imgurProject.model.Member;
import com.example.imgurProject.repo.ImageRepo;
import com.example.imgurProject.repo.MemberRepo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ImageService {
	@Autowired
    private MemberRepo memberRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Value("${imgur.uploadUrl}")
    private String uploadUrl;

    @Value("${imgur.deleteUrl}")
    private String deleteUrl;

    @Value("${imgur.clientId}")
    private String clientId;

    @Value("${imgur.client.access_token}")
    private String clientAccessToken;
	public void uploadImage(MultipartFile file, String userName) {
		ImgurResponseVO response = uploadImageService(file,userName);
		String imgurUrl = response.getData().getLink();
        log.info("Image uploaded successfully, URL: {}", imgurUrl);
       try {
       
        Member user = memberRepo.findByUserName(userName);
        Image image = new Image();
        image.setImageUrl(imgurUrl);
        image.setMember(user);
        image.setDeleteHash(response.getData().getDeletehash());
        imageRepo.save(image);
        log.info(image.getImageId());
       }catch(Exception e)
       {
    	   throw new CustomException("User not found","USER_NOT_FOUND");
       }
	}
	public void deleteImage(String imageId) {
        try {
            Image image = imageRepo.findById(imageId)
                    .orElseThrow(() -> new CustomException("Image not found with the Image Id : " + imageId, "IMG_NOT_FOUND"));

            log.info("Deleting image from Imgur API with delete hash: {}", image.getDeleteHash());
            deleteImageService(image.getDeleteHash());

            log.info("Deleting image from repo with image ID: {}", imageId);
            imageRepo.delete(image);
        } catch (CustomException e) {
            log.error("Error during image deletion: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred during image deletion: {}", e.getMessage(), e);
            throw new CustomException("An unexpected error occurred", "UNEXPECTED_ERROR");
        }
    }
	private void deleteImageService(String imageHash) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer " + clientAccessToken);
	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    try {
	        RestTemplate restTemplate = new RestTemplate();
	        String finaldeleteUrl = deleteUrl + imageHash;
	        ResponseEntity<Map> response = restTemplate.exchange(finaldeleteUrl, HttpMethod.DELETE, entity, Map.class);
	        if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                if (body != null && Boolean.TRUE.equals(body.get("success"))) {
                    log.info("Image deleted successfully from Imgur.", body);
                } else {
                    throw new CustomException("Imgur API deletion failed: " + body, "IMG_DELETION_ERROR");
                }
            } else {
                throw new CustomException("Imgur API deletion failed with status: " + response.getStatusCode(), "IMG_DELETION_ERROR");
            }

        } catch (Exception e) {
            log.error("Unexpected error during image deletion: {}", e.getMessage(), e);
            throw new CustomException("An unexpected error occurred during image deletion", "UNEXPECTED_ERROR");
        }
	}

	private ImgurResponseVO uploadImageService(MultipartFile file, String userName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + clientAccessToken);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        try {
        	 RestTemplate restTemplate = new RestTemplate();
        	ResponseEntity<ImgurResponseVO> responseVO = restTemplate.exchange( uploadUrl, HttpMethod.POST, entity, ImgurResponseVO.class);
        	ImgurResponseVO res = responseVO.getBody();
            if (res != null && res.isSuccess()) {
                return res;
            } else {
                throw new RuntimeException("Image upload failed: " + res.getStatus_code());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Image upload failed: " + e.getMessage());
        }
	}
	public List<ImageDetailsVO> getAllImages(String userName) {
		try {
			Member member=memberRepo.findByUserName(userName);
			List<Image> images = imageRepo.findByMemberMemberId(member.getMemberId());
			List<ImageDetailsVO> listOfImages = new ArrayList<>();
			for(Image image:images)
			{
				ImageDetailsVO temp=new ImageDetailsVO(image.getImageId(),image.getImageUrl());
				listOfImages.add(temp);
			}
			return listOfImages;
		}catch (Exception e) {
            log.error("An error occurred while retrieving images for user: {}", userName, e);
            throw new RuntimeException("Failed to retrieve images", e);
        }
	}
	public MemberProfileVO getUserProfileWithImages(String userName) {
		try {
			Member member=memberRepo.findByUserName(userName);
			List<Image> images = imageRepo.findByMemberMemberId(member.getMemberId());
			MemberResponseVO memberDetails=new MemberResponseVO(
					member.getMemberId(),
					member.getUserName(), 
					member.getEmailId(), 
					member.getAge(),
					member.getGender());
			List<ImageDetailsVO> listOfImages = new ArrayList<>();
			for(Image image:images)
			{
				ImageDetailsVO temp=new ImageDetailsVO(image.getImageId(),image.getImageUrl());
				listOfImages.add(temp);
			}
			return new MemberProfileVO(memberDetails,listOfImages);
		}catch (Exception e) {
            log.error("An error occurred while retrieving images for user: {}", userName, e);
            throw new RuntimeException("Failed to retrieve images", e);
        }
	}
    
}
