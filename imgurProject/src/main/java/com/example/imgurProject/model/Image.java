package com.example.imgurProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Image {
	   @Id
	   @GeneratedValue(strategy = GenerationType.UUID)
	   private String imageId;
	   @Column(nullable = false)
	    private String imageUrl;

	    @Column(nullable = false)
	    private String deleteHash;

	    @ManyToOne
	    @JoinColumn(name = "member_id")
	    private Member member;
}
