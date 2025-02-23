package com.example.imgurProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private String memberId;
   @Column(name = "USER_NAME", unique = true, nullable = false)
   private String userName;
   @Column(name = "PASSWORD", nullable = false)
   private String password;
   @Column(name = "EMAIL_ID")
   private String emailId;
   @Column(name = "AGE")
   private int age;
   @Column(name = "GENDER")
   private String gender;
  
}
