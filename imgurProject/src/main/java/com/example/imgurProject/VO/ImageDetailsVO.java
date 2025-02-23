package com.example.imgurProject.VO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDetailsVO {
	   private String imageId;
	    private String imageUrl;
}
