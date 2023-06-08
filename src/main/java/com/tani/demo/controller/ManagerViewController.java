package com.tani.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManagerViewController {
	
	@GetMapping("/manager/join")//폴더이름- .html 파일이름
	public void join() {
	}
	
	/*
	@GetMapping("/apple")
	public String jjoin() {
		return "/memeber/join";
	}
	*/
	
//	@GetMapping("/member/find")
//	public void find() {
//	}
	
	// ModelAndView -> String login(Model model)
	@GetMapping("/manager/login")
	public void login(HttpSession session, Model model) {
		if(session.getAttribute("msg")!=null) {
			// 세션에 있는 데이터는 사라지지않음 , 모델에있는 데이터는 사라짐
			model.addAttribute("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
	}
	
	
	

	@GetMapping("/manager/home")
	public void home() {
	}
	
	@GetMapping("/manager/loginsuc")
	public void loginsuc() {
		
	}
	
	@GetMapping("/manager/read")
	public void read() {
		
	}
	
	@GetMapping("/manager/findBysId")
	public void findBybId() {
		
	}
	
	@GetMapping("/manager/resetPassword")
	public void resetPassword() {
		
	}
	
	@GetMapping("/manager/changePassword")
	public ModelAndView changepassword(HttpSession session) {
		ModelAndView mav = new ModelAndView("/manager/changePassword");
		if(session.getAttribute("msg")!=null) {
			mav.addObject("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
		return mav;
	}
	@GetMapping("/manager/resign")
	public void resign() {
		
	}


}
