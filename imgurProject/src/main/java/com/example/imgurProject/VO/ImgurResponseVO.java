package com.example.imgurProject.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgurResponseVO {
	private String status_code;
    private boolean success;
    private ImgurDataVO data;
}
