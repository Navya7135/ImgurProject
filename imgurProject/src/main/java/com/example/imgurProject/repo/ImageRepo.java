package com.example.imgurProject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.imgurProject.model.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, String>{
	 List<Image> findByMemberMemberId(String memberId);
}
