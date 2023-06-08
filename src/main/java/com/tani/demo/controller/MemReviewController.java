package com.tani.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tani.demo.dto.ReviewDto;
import com.tani.demo.dto.ResponseDto;
import com.tani.demo.service.MemReviewService;

@RestController
@RequestMapping(value="/member")
public class MemReviewController {
   @Autowired
   MemReviewService service;
   
   @PostMapping(value="/acc/detail/review/save", produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<ResponseDto> save(ReviewDto.SaveReview dto, Principal principal) {
	   System.out.println(dto);
	   service.saveReview(dto);
      return ResponseEntity.ok(new ResponseDto("한줄평 작성이 완료되었습니다", principal.getName()));
   }
 
}

