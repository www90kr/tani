package com.tani.demo.entity;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Orderr {
	private Integer orderNo;			// 주문번호
	private String username;			// 회원코드
	private LocalDate orderDate;		// 주문날짜
	private String shipName;			// 배송지명
	private String shipAddr;			// 배송주소 
	private Integer orderPrice;  		// 주문금액 
	

	public void addTotalPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}
}




