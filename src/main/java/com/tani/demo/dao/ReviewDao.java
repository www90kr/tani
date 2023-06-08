package com.tani.demo.dao;


import org.apache.ibatis.annotations.Mapper;

import com.tani.demo.dto.ReviewDto;

@Mapper
public interface ReviewDao {

	
   // 댓글 등록
   public Integer saveReview(ReviewDto.SaveReview dto);
   
   

}