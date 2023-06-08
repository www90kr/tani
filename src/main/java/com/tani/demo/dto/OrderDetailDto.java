package com.tani.demo.dto;

import com.tani.demo.entity.OrderDetail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDetailDto {

	@Data
	@Builder
	public static class SaveDto {
		private Integer orderNo;		//주문번호
		private Integer code;			//용품코드
		private Integer cartNum;		//용품수량	
		
		public OrderDetail toEntity() {
			return OrderDetail.builder().orderNo(orderNo). code(code). cartNum(cartNum).build();
		}
}
	
	@Data
	public static class PriceDto {
		private Integer code;
		private Integer price;
		private Integer cartNum;
	}
}
