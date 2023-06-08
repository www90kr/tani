package com.tani.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/manager")
public class ManagerBoardViewController {
	

    // ===============photo File data=====================
    // 확장자로 데이터의 MIME 타입을 리턴하는 함수
    // jpg, png, gif -> 확장자가 틀리면 브라우저가 제대로 처리못할수도????
    private MediaType getMediaType(String imagename) {
        // spring11.jpg -> .을 찾아서 .다음부터 자르면 확장자
        int position = imagename.lastIndexOf(".");
        String ext = imagename.substring(position + 1).toUpperCase();
        if (ext.equals("JPG"))
            return MediaType.IMAGE_JPEG;
        else if (ext.equals("PNG"))
            return MediaType.IMAGE_PNG;
        else
            return MediaType.IMAGE_GIF;
    }

    //
    
	// 게시판 리스트
	  	@GetMapping("/board/list")
		public ModelAndView boardlist() {
	  		return new ModelAndView("/manager/board/list");
		}
	  	
	  	// 게시판 작성
		@GetMapping("/board/write")
		public ModelAndView  boardWrite() {
			return new ModelAndView("/manager/board/write");
		}
		
		
		// 게시판 읽기
		@GetMapping("/board/read")
		public ModelAndView  boardRead() {
			return new ModelAndView("/manager/board/read");
		}
}
