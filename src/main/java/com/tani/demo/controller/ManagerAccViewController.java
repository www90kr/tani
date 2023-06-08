package com.tani.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManagerAccViewController {
   // 용품 메인
   @GetMapping("/manager/acc")
   public ModelAndView list() {
      return new ModelAndView("manager/acc/acc");
   }

   // 용품 메인 카테(텐트) 
   @GetMapping("/manager/acc/sort1") 
   public ModelAndView tent() { 
	  return new ModelAndView("manager/acc/accSort1"); 
   }
	  
   // 용품 메인 카테(매트)
   @GetMapping("/manager/acc/sort2") 
   public ModelAndView mat() { 
	   return new ModelAndView("manager/acc/accSort2"); 
   }

   // 용품 메인 카테(테이블)
   @GetMapping("/manager/acc/sort3") 
   public ModelAndView table() { 
	   return new ModelAndView("manager/acc/accSort3"); 
   }

   // 용품 메인 카테(식기)
   @GetMapping("/manager/acc/sort4") 
   public ModelAndView dish() { 
	   return new ModelAndView("manager/acc/accSort4"); 
   }

   // 용품 메인 카테(기타)
   @GetMapping("/manager/acc/sort5") 
   public ModelAndView etc() { 
	   return new ModelAndView("manager/acc/accSort5"); 
   }
   
   // 용품 상세
   @GetMapping("/manager/acc/acc_detail")
   public ModelAndView read() {
	   return new ModelAndView("manager/acc/acc_detail"); 
   }
  	
}