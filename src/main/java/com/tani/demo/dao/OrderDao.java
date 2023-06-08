package com.tani.demo.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tani.demo.dto.BuyListDto;
import com.tani.demo.entity.Orderr;

@Mapper
public interface OrderDao {

	// 주문처리 
	public Integer orderSave(Orderr order);

	// 총합 업데이트
	public Integer orderPriceUpdate(Integer orderPrice, Integer orderNo , String username);
	
	// 장바구니 전체삭제(주문 후)
	public Integer cartAllDelete(String username);
	
	
	public List<BuyListDto.BuyList> buylistGet(Integer orderNo, String username);
	
	
	public Integer updateStatus(Integer odtNo);

}