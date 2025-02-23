package com.example.imgurProject.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponseVO {
	    private String imageId;
	    private String imageUrl;
	    private MemberResponseVO member;
}
