package com.tani.demo.service; 


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tani.demo.dao.OrderDao;
import com.tani.demo.dao.OrderDetailDao;
import com.tani.demo.dto.BuyListDto;
import com.tani.demo.dao.CartDao;
import com.tani.demo.dto.OrderDto;
import com.tani.demo.dto.OrderDetailDto;
import com.tani.demo.entity.Orderr;
import com.tani.demo.entity.OrderDetail;

@Service
public class OrderService {
	
	@Autowired
	OrderDetailDao detaildao;
	
	@Autowired
	OrderDao orderdao;
	
	@Autowired
	CartDao cartdao;
	
	// 주문하기 Service 
	public Orderr orderSave(OrderDto.orderSave ordersavedto) { // 하나의 주문이 저장되는 서비스 로직이다  (파라미터: 주문그릇)
		
		Orderr order = ordersavedto.toentity();				//내주문그릇을 진짜db에 저장하기 위한 변수세팅(저장공간마련)
			
		order.addTotalPrice(0);								//내주문의 총가격을 업뎃시켜주는함수 (일단 0으로 초기화)
		
		orderdao.orderSave(order);							//...일단 저장중...(저장공간확보)
		
		
		//장바구니에서 데이터 가져와 상세에 각각 담기 
		for(int i=0; i<cartdao.cartListSize(ordersavedto.getUsername());i++) {
			OrderDetailDto.SaveDto dto = OrderDetailDto.SaveDto.builder().orderNo(order.getOrderNo()).code(detaildao.cartListGet(ordersavedto.getUsername()).get(i).getCode())
					.cartNum(detaildao.cartListGet(ordersavedto.getUsername()).get(i).getCartNum()).build();
			OrderDetail result = dto.toEntity();
			detaildao.orderDetailSave(result);
		}
		
		// 상품 가격,수량 리스트에 담기 
		List<Integer> total = new ArrayList<>();
		int totalPirce = 0; 		
		List<OrderDetailDto.PriceDto> value = detaildao.OrderInfo(order.getOrderNo(), order.getUsername());
		
	
		// 주문번호 목록 가져와서 금액 계산  
		for(int i=0; i< detaildao.OrderDetailByOrder(order.getOrderNo()); i++) {
			int count = value.get(i).getCartNum();
			int price = value.get(i).getPrice();
			
			total.add(i, count * price);
	}
		for(int i=0; i<total.size(); i++) {
			totalPirce = totalPirce + total.get(i);
		}
		
		order.addTotalPrice(totalPirce);
		
		// 금액 업데이트
		orderdao.orderPriceUpdate(order.getOrderPrice(),order.getOrderNo(),order.getUsername());
		//장바구니 삭제
		orderdao.cartAllDelete(ordersavedto.getUsername());
		
		return order;
		}
	

		public  List<BuyListDto.BuyList> buylistGet(Integer odtNo, String username) {	
		return orderdao.buylistGet(odtNo, username);
		}
	
	
	}
	
	
