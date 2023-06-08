package com.tani.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tani.demo.dto.AccDto;
import com.tani.demo.dto.ResponseDto;
import com.tani.demo.dto.AccDto.CategoryList;
import com.tani.demo.dto.PageDto;
import com.tani.demo.service.AccService;

@RestController
@RequestMapping(value="/anony")
public class AccController {
   @Autowired
   private AccService service;
   
   // 카테고리
   @GetMapping(value="/acc/category", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<CategoryList>> ReadCateList(String cateCode){
      return ResponseEntity.ok(service.ReadCateList(cateCode));

   }
   
   // 글목록 출력
   @GetMapping(value = "/acc/list", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<PageDto> findAll(@RequestParam(defaultValue = "1") Integer pageno, Integer code) {
      System.out.println("==========================");
      System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
      System.out.println(service.findAll(pageno, null));
      
      return ResponseEntity.ok(service.findAll(pageno, null));
   }
   
   // 글 상세
   @GetMapping(value="/acc/detail", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<AccDto.AccDetailWithReadReviewList> detail(Integer code){
      return ResponseEntity.ok(service.detail(code));
      
   }
   
   // 평점 평균
   @PutMapping(value="/acc/starAvg", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<AccDto.UpdateStar> setRating(Integer code){
	   return ResponseEntity.ok(service.setRating(code));
   }

   // 한줄평(in상세) 출력
   @GetMapping(value="/acc/detail/review", produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<ResponseDto> read(Integer code){
      return ResponseEntity.ok(new ResponseDto("상세페이지의 용품 리뷰 출력" ,service.reviewList(code)));
   }
}

