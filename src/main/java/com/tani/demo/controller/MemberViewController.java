package com.tani.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberViewController {
	
	@GetMapping("/member/join")//폴더이름- .html 파일이름
	public void join() {
	}
	
	/*
	@GetMapping("/apple")
	public String jjoin() {
		return "/memeber/join";
	}
	*/
	@GetMapping("/member/page")
	public void page() {
		
	}

	
	// ModelAndView -> String login(Model model)
	@GetMapping("/member/login")
	public void login(HttpSession session, Model model) {
		if(session.getAttribute("msg")!=null) {
			// 세션에 있는 데이터는 사라지지않음 , 모델에있는 데이터는 사라짐
			model.addAttribute("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
	}
	
	
	

	@GetMapping("/member/home")
	public void home() {
	}
	

	
	@GetMapping("/member/read")
	public void read() {
		
	}
	
	
	@GetMapping("/member/findById")
	public void findById() {
		
	}
	
	@GetMapping("/member/resetPassword")
	public void resetPassword() {
		
	}
	
	@GetMapping("/member/changePassword")
	public ModelAndView changepassword(HttpSession session) {
		ModelAndView mav = new ModelAndView("/member/changePassword");
		if(session.getAttribute("msg")!=null) {
			mav.addObject("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
		return mav;
	}
	@GetMapping("/member/resign")
	public void resign() {
		
	}


}
