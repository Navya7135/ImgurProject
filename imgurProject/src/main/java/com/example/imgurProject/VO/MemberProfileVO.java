package com.example.imgurProject.VO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfileVO {
     private MemberResponseVO member;
     private List<ImageDetailsVO> images;
}
