package com.example.imgurProject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.imgurProject.model.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, String>{

	Member findByUserName(String userName);

}
