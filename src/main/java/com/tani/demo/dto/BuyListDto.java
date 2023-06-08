package com.tani.demo.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuyListDto {
	// 구매내역 가져오기
	@Data
	public static class BuyList{
		private LocalDate orderDate;		// 주문날짜
		private String mainImg;			// 용품사진
		private String name;				// 주문용품이름
		private Integer price;			// 주문용품가격
		private Integer cartNum;			// 주문용품수량
		private String odtStatus;	// 주문용품상태
		private Integer odtNo; 			// 주문용품번호
		private Integer orderNo;			// 주문번호
		private Integer code;				// 용품코드	
	}
}
