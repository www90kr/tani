package com.tani.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tani.demo.dao.OrderDao;
import com.tani.demo.dao.ReviewDao;
import com.tani.demo.dao.AccDao;
import com.tani.demo.dto.ReviewDto;

@Service
public class MemReviewService {
   @Autowired
   ReviewDao rdao;
   @Autowired
   AccDao adao;
   @Autowired
   OrderDao odao;
   @Autowired
   AccService service;
   
   public ReviewDto.SaveReview saveReview(ReviewDto.SaveReview dto) {
//	      Review pdtReview = dto.toEntity();
//
//	      pdtReview.addWriter("spring");
	      rdao.saveReview(dto);
	      odao.updateStatus(dto.getOdtNo());
	      

	      return dto;
	   }



//   public List<ReviewDto.ReadReviewList> write(ReviewDto.SaveReview dto, String loginId){
//      dao.saveReview(dto.toEntity().addWriter(loginId));
//      return dao.findById(dto.getPOdtNo());
//   }

//   @Transactional
//   public List<ReviewDto.ReadReviewList> delete (ReviewDto.DeleteReview dto, String loginId){
//      String bId = dao.findById(dto.getPReviewNo()).orElseThrow(ReviewNotFoundException::new);
//      if(!bId.equals(loginId))
//         throw new JobFailException("삭제할 수 없습니다");
//      dao.deleteReview(dto.getPCode());
//      return dao.findByPCode(d)
//   }

}