package com.tani.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemAccViewController {
   // 용품 메인
   @GetMapping("/member/acc")
   public ModelAndView list() {
      return new ModelAndView("member/acc/acc");
   }

   // 용품 메인 카테(텐트) 
   @GetMapping("/member/acc/sort1") 
   public ModelAndView tent() { 
	  return new ModelAndView("member/acc/accSort1"); 
   }
	  
   // 용품 메인 카테(매트)
   @GetMapping("/member/acc/sort2") 
   public ModelAndView mat() { 
	   return new ModelAndView("member/acc/accSort2"); 
   }

   // 용품 메인 카테(테이블)
   @GetMapping("/member/acc/sort3") 
   public ModelAndView table() { 
	   return new ModelAndView("member/acc/accSort3"); 
   }

   // 용품 메인 카테(식기)
   @GetMapping("/member/acc/sort4") 
   public ModelAndView dish() { 
	   return new ModelAndView("member/acc/accSort4"); 
   }

   // 용품 메인 카테(기타)
   @GetMapping("/member/acc/sort5") 
   public ModelAndView etc() { 
	   return new ModelAndView("member/acc/accSort5"); 
   }
   
   // 용품 상세
   @GetMapping("/member/acc/acc_detail")
   public ModelAndView read() {
	   return new ModelAndView("member/acc/acc_detail"); 
   }

   // 용품 장바구니
   @GetMapping("/member/acc/cart")
   public ModelAndView listCart() {
      return new ModelAndView("member/acc/cart");
   }
   
   // 용품 주문
   @GetMapping("/member/acc/order")
   public ModelAndView orderlist() {
       return new ModelAndView("member/acc/order");
   }
   
   // 주문내역 출력
   @GetMapping("/member/acc/order/buylist")
   public ModelAndView buylist() {
       return new ModelAndView("member/acc/buylist");
   }
   
   // 용품 주문완료.
  	@GetMapping("/member/acc/orderfin")
	public ModelAndView orderfin() {
  		return new ModelAndView("member/acc/orderfin");
  	}
  	
}