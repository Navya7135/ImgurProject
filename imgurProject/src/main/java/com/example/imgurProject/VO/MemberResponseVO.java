package com.example.imgurProject.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberResponseVO {
	   public MemberResponseVO(String memberId, String userName, String emailId, int age, String gender) {
		super();
		this.memberId = memberId;
		this.userName = userName;
		this.emailId = emailId;
		this.age = age;
		this.gender = gender;
	}
	private String memberId;
	   private String userName;
	   private String password;
	   private String emailId;
	   private int age;
	   private String gender;
	
}
