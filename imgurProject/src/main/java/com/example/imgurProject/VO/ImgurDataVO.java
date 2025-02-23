package com.example.imgurProject.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgurDataVO {
	private String id;
    private String title;
    private String desc;
    private String type;
    private String link;
    private String deletehash;
}
