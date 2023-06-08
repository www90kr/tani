package com.tani.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.tani.demo.dto.CartDto;
import com.tani.demo.entity.Cart;

@Mapper
public interface CartDao {
	
	
	// 동일상품 확인 
	public boolean checkCart(Integer code, String username); 
	
	// 장바구니 추가 
	public Integer addCart(Cart cart);
	
	// 기존 장바구니에 추가 
	public Integer cartPlus(Integer code, Integer cartNum, String username);
	
	// 장바구니 리스트 
	public List<CartDto.Read> listCart(String username);
	
	
	// 장바구니 삭제
	public int cartDelete(String username, Integer cartNo) ;
	
	// 장바구니 조회(삭제시 필요) 	
	public Optional<Integer> findByCartNo(Integer code);
	

	
	// 수량 플러스
	public Integer cartNumPlusCount(Integer code);
	
	// 수량 마이너스
	public Integer cartNumMinusCount(Integer cartNo);
	
	// 장바구니 수량 가져오기(수량변경시 필요)
	public Optional<Integer> getCount(Integer cartNo);
	
	
	// 장바구니 용품갯수(구매하기 저장시 필요)
	public Integer cartListSize(String username);
}
