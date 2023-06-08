package com.tani.demo.dto;

import java.util.List;

import com.tani.demo.entity.Cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDto {

	@Data
	@Builder	
	public static class AddCart{ 		//카트에담기
		private Integer cartNum;		//장바구니수량
		private String username;		//회원코드
		private Integer code;			//상품코드
		
		public Cart toEntity() {
			return Cart.builder().cartNum(cartNum).username(username).code(code).build();
		}
	}
		
	@Data
	@ToString
	public static class Read{		//카트읽어오기
		private Integer cartNo;  	//카트 순번No
		private String username; 	//회원코드
			
		private Integer code; 		//상품코드
		private String name;		//상품이름
		private Integer price;		//상품가격
		private Integer cartNum; 	//장바구니수량
		private String mainImg;		//상품이미지
		
		//public void initTotalPrice() {
		//this.totalPrice = this.pPrice*this.cartNum;
		//}
	}
	
	@Builder
	@Data
	public static class cartCountUpdate { //카트상품 수량직접변경
		private Integer cartNo;			//카트 순번No
		private Boolean countVal;		//카트 수량 + - 버튼 값 
		private Integer code;			//상품코드
		private String username;		//회원코드
	}
	
	@Data
	public static class delete{
		private List<Integer> cartNo;		//카트 순번No 리스트 (삭제할리스트)
		private String username;			//회원코드
	}
	
	@Data
	@Builder
	public static class result {
		private List<CartDto.Read> list;	
		private Integer total;
	}
	
	@Data
	public static class list{
		private Integer cartNum;
		private Integer code;
	}
	

	
}
