package com.tani.demo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tani.demo.dto.CartDto;
import com.tani.demo.dto.ResponseDto;
import com.tani.demo.service.CartService;

@RestController
@RequestMapping(value="/member")
public class MemCartController {
	
	@Autowired 	
	private CartService service;
	
	// 장바구니 추가 
	@PostMapping("/acc/cart/add")
	public String addCartPOST(CartDto.AddCart cart, HttpServletRequest request) throws Exception {
		// 카트 등록
		String result = service.AddCart(cart);
		
		return result + "";
	}

	
	//	장바구니 리스트
	@GetMapping(value = "/cart/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CartDto.Read>> listCart(Principal principal){ // 로그인 아이디로 대체
	
		return ResponseEntity.ok(service.listCart(principal.getName()));
	 }

	// 수량변경
	@PutMapping(value = "/cart/countupdate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> update(CartDto.cartCountUpdate dto, Principal principal){
		
		return ResponseEntity.ok(new ResponseDto("수량 변경 완료", service.UpdateCount(dto)));
	}
	
	//장바구니 삭제
	@DeleteMapping(value = "/cart/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> delete(CartDto.delete list, Principal principal){
		
		service.cartDelete(list);
		return ResponseEntity.ok(new ResponseDto("상품이 삭제되었습니다", service.listCart(principal.getName())));

	}
}
