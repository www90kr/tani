package com.tani.demo.entity;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class BuyList {
	private LocalDate orderDate;		// 주문날짜
	private String name;				// 주문용품이름
	private Integer price;				// 주문용품가격
	private Integer cartNum;			// 주문용품수량
	private String odtStatus;	// 주문용품상태
	private Integer odtNo; 			// 주문용품번호
	private Integer code;				// 용품코드


}
