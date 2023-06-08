package com.tani.demo.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.tani.demo.dto.AccDto;
import com.tani.demo.dto.ReviewDto;

@Mapper
public interface AccDao {
   // 카테고리별 목록 읽어오기
   public List<AccDto.CategoryList> ReadCateList(String cateCode);
   
   // 전체 목록 리스트 읽어오기
   public List<AccDto.AccList> AccList();
   
   // 용품 개수 (for 페이징)
   public Integer countAcc(Integer code);
    
   // 전체목록 리스트 읽어오기 (페이징)
   public List<AccDto.AccList> findAll(Map map);                                          
   
   // 상세페이지 
   public Optional<AccDto.AccDetail> AccDetail(Integer code);
   
   // 별점 평균 구하기
   public Double getStarAvg(Integer code);
   
   // 별점 평균 업뎃
   public Integer updateStarAvg(AccDto.UpdateStar dto);
   
   // 한줄평 개수 ( for 별점평균)
   public Integer countReview(Integer reviewNo);
   
   // 한줄평 목록
   public List<AccDto.ReadReviewList> reviewList(Integer code);
            
   
}