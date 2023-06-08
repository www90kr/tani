package com.tani.demo.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class AccDto {
	//카테고리리스트
   @Data
   public static class CategoryList{
	   private Integer code;	//상품코드
	   private String name;		//상품명
	   private String mainImg; 	//상품썸네일
	   private Integer price;	//상품가격
	   private String cateCode;	//상품카테고리코드
       private String cateName;	//상품카테고리명 (이게 여기 있어도되는건지모르겠음)
   }
   
   // 전체목록
   @Data		
   public static class AccList {
	   private Integer code;	//상품코드
	   private String name;		//상품명
	   private String mainImg; 	//상품썸네일
	   private Integer price;	//상품가격
	   private String cateCode;	//상품카테고리코드
   }
   

   // 상세페이지 
   @Data
   @Builder
   public static class AccDetail {
	   private Integer code;	//상품코드
	   private String name;		//상품명
	   private String mainImg; 	//상품썸네일
	   private String detailImg;	//상품상세이미지
	   private Double starAvg;		//상품대표별점
	   private Integer price;		//상품가격
      
      
	public void addAvgStar(Double starAvg) {		//상품대표별점 리셋함수 (이미 계산하여 업뎃된 리뷰평점을 화면에 리셋해주는 함수)
		this.starAvg = starAvg;						// ***리턴타입을 void로 준다는 사실과 this 로 부른다는 사실을 잊지말기 
	}
   }
   
   
   // 리뷰리스트 
   @Data
   public static class ReadReviewList{
      private Integer reviewNo;			//리뷰번호
	  private Integer Code;				//상품코드
      private Integer Star;				//상품리뷰 개당 별점
      private String reviewContent;		//상품리뷰 개당 내용
      private String username;			//상품리뷰 글쓴아이디
      @JsonFormat(pattern="yyyy-MM-dd")	//상품리뷰 작성일 (jsonformat) 저 형식이 가장 보편화됨 공식처럼 외우기
      private LocalDate ReviewDate;		//상품리뷰 작성일
   }
   
   //상세페이지+ 리뷰리스트
   //구조설명 
   //클래스 Dto(1) 안에 클래스 Dto(1-1) + 클래스 Dto(1-2) 가 결합 = 클래스 Dto(1-5) 를 만들어냄 >>>이렇게도 가능!
   @Data
   public static class AccDetailWithReadReviewList {
	   private AccDetail accDetail;
	   private List<ReadReviewList> readReviewLists;
	
   }
   
   //리뷰평점업뎃 (리뷰평점 계산업뎃을 위한 Dto)
   @Data
   public static class UpdateStar{
	   private Integer Code;
	   private Double StarAvg;
   }
}