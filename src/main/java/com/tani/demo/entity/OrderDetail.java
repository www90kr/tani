package com.tani.demo.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class OrderDetail {
	private Integer odtNo; 		//주문용품번호
	private Integer orderNo;		//주문번호
	private Integer code;			//용품코드
	private Integer cartNum;		//용품수량	
	private String odtStatus;	// 주문상태
	
}
	
