package com.tani.demo.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tani.demo.dto.BoardDto;
import com.tani.demo.dto.RestResponse;
import com.tani.demo.entity.Board;
import com.tani.demo.service.BoardService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value="/manager")
public class ManagerBoardController {

	@Autowired
	private BoardService service;
	
	

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
	 
	//  글저장
	@PostMapping(value = "/board/save", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> boardSave(BoardDto.boardSave dto, Principal principal){
		Board board = service.boardSave(dto, principal.getName());
		return ResponseEntity.ok(new RestResponse("OK", board, "/board/read?bno=" + board.getBno()));
	}
	
	// 리스트 
	@GetMapping(value="/board/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> findAll(@RequestParam(defaultValue = "1") Integer pageno, @ApiIgnore Principal principal) {
		return ResponseEntity.ok(new RestResponse("OK", service.findAll(pageno, principal.getName()), null));
	}
	
	// 글읽기
	@GetMapping(value="/board/read/rest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> read(@RequestParam Integer bno, @ApiIgnore Principal principal) {
		BoardDto.read dto = service.read(bno, principal.getName());
		return ResponseEntity.ok(new RestResponse("OK", dto, null));
	}
}
