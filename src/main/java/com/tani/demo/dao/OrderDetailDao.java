package com.tani.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tani.demo.dto.CartDto;
import com.tani.demo.dto.OrderDetailDto;
import com.tani.demo.entity.OrderDetail;

@Mapper
public interface OrderDetailDao {
	
	// 주문상품 추가
	public Integer orderDetailSave(OrderDetail detail);
	
	// 장바구니 목록 가져오기
	public List<CartDto.list> cartListGet(String username);

	// 상품 가격,수량 가져오기
	public List<OrderDetailDto.PriceDto> OrderInfo(Integer orderNo, String username);
		
	// 주문번호 목록 가져오기
	public Integer OrderDetailByOrder(Integer orderNo); //주문번호목록가져온다면서 왜 인티저가 리턴되는지모르겠다 ;; 
	
	// 주문내역 삭제
	public Integer OrderDelete(Integer orderNo, String username);
}
