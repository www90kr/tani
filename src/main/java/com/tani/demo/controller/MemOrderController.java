package com.tani.demo.controller;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tani.demo.dto.OrderDto;
import com.tani.demo.dto.ResponseDto;
import com.tani.demo.service.MemberService;
import com.tani.demo.service.OrderService;


@RestController
@RequestMapping(value="/member")
public class MemOrderController {
	
	@Autowired
	private OrderService Oservice;
	
	@Autowired
	private MemberService Mservice;
	


	
	
	
	// 주문하기 
	@PostMapping("/acc/order/add")
	public ResponseEntity<ResponseDto> order(OrderDto.orderSave orderSavedto) {
		System.out.println(orderSavedto);
		System.out.println(Oservice.orderSave(orderSavedto));
		return ResponseEntity.ok(new ResponseDto("상품 주문 완료 입니다", Oservice.orderSave(orderSavedto)));
	}
	

	
	// 회원정보 가져오기
	@GetMapping(value = "/acc/order/memberinfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> memberinfo(Principal principal) {
		
		return ResponseEntity.ok(new ResponseDto(principal.getName(), Mservice.MemberInfo(principal.getName())));
	}
	
	
	// 구매내역 출력 
	@GetMapping(value = "/acc/buylist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> buylistGet(Integer odtNo, Principal principal) {
		return ResponseEntity.ok(new ResponseDto("주문내역 출력", Oservice.buylistGet(odtNo, principal.getName())));
	}




}
