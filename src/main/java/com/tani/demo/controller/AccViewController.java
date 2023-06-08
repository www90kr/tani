package com.tani.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccViewController {
   // 용품 메인
   @GetMapping("/anony/acc")
   public ModelAndView list() {
      return new ModelAndView("anony/acc/acc");
   }

   // 용품 메인 카테(텐트) 
   @GetMapping("/anony/acc/sort1") 
   public ModelAndView tent() { 
	  return new ModelAndView("anony/acc/accSort1"); 
   }
	  
   // 용품 메인 카테(매트)
   @GetMapping("/anony/acc/sort2") 
   public ModelAndView mat() { 
	   return new ModelAndView("anony/acc/accSort2"); 
   }

   // 용품 메인 카테(테이블)
   @GetMapping("/anony/acc/sort3") 
   public ModelAndView table() { 
	   return new ModelAndView("anony/acc/accSort3"); 
   }

   // 용품 메인 카테(식기)
   @GetMapping("/anony/acc/sort4") 
   public ModelAndView dish() { 
	   return new ModelAndView("anony/acc/accSort4"); 
   }

   // 용품 메인 카테(기타)
   @GetMapping("/anony/acc/sort5") 
   public ModelAndView etc() { 
	   return new ModelAndView("anony/acc/accSort5"); 
   }
   
   // 용품 상세
   @GetMapping("/anony/acc/acc_detail")
   public ModelAndView read() {
	   return new ModelAndView("anony/acc/acc_detail"); 
   }
  	
}