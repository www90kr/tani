package com.tani.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tani.demo.dao.AccDao;
import com.tani.demo.dto.AccDto;
import com.tani.demo.dto.AccDto.AccDetailWithReadReviewList;
import com.tani.demo.dto.PageDto;

@Service
public class AccService {
   @Autowired
   private AccDao dao;
//   @Value("${campass.pagesize}")
   private Integer pagesize=9;
   
   // 카테고리 리스트
   public List<AccDto.CategoryList> ReadCateList(String cateCode){
	   return dao.ReadCateList(cateCode);
   }
   
   // 용품 리스트 출력
   public List<AccDto.AccList> list(){
      return dao.AccList();   
   }
   
   // 용품 페이징  
   public PageDto findAll(Integer pageno, Integer code) {
      Integer totalcount = dao.countAcc(code);
      Integer countOfPage = (totalcount-1)/pagesize + 1;
      
      if(pageno>countOfPage)
         pageno=countOfPage;
      else if(pageno<0)
         pageno=-pageno;
      else if(pageno==0)
         pageno=1;
      
      Integer start = (pageno-1) * pagesize + 1;
      Integer end = pageno * pagesize;
      
      Map<String,Object> map = new HashMap<>();
      map.put("start", start);
      map.put("end", end);
      map.put("code", code);
      return new PageDto(pageno,pagesize,totalcount,dao.findAll(map));
   }
   
   // 용품 상세페이지 출력
   public AccDto.AccDetailWithReadReviewList detail(Integer code){
	  AccDetailWithReadReviewList dto = new AccDto.AccDetailWithReadReviewList(); 
	  AccDto.AccDetail value = dao.AccDetail(code).get();
	  value.addAvgStar(dao.getStarAvg(code));
	  dto.setAccDetail(value);
	  dto.setReadReviewLists(dao.reviewList(code));
      return dto;
   }
   
   // 별점 평균
   public AccDto.UpdateStar setRating(Integer code) {
	   Double starAvg = dao.getStarAvg(code);
	 
	   AccDto.UpdateStar dto = new AccDto.UpdateStar();
	   dto.setCode(dao.countReview(code));
	   dto.setStarAvg(dao.getStarAvg(code));
	   
	   setRating(dto.getCode());
	   
	   dao.updateStarAvg(dto);
	return dto;
   }
   
   // 상세페이지 안에 있는 한줄평 출력
   public List<AccDto.ReadReviewList> reviewList(Integer code){
      return dao.reviewList(code);
   }
}