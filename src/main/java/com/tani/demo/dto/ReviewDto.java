package com.tani.demo.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tani.demo.dto.AccDto.AccDetail;
import com.tani.demo.entity.Review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class ReviewDto {
	
	   @Data
	   public static class SaveReview {
		  private Integer reviewNo;
		  private Integer odtNo;
		  private String username;
	      @NotEmpty
	      private Integer Star;
	      @NotEmpty
	      private String reviewContent;
	      private Integer code;

	      public Review toEntity() {
	         return Review.builder().reviewNo(reviewNo).odtNo(odtNo).username(username).build();
	      }
	   }

	   @Data
	   public static class DeleteReview {
	      @NotNull
	      private Integer reviewNo;
	      @NotNull
	      private Integer code;
	   }
	




}