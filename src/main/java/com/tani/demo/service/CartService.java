package com.tani.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tani.demo.dao.CartDao;
import com.tani.demo.dto.CartDto;
import com.tani.demo.dto.CartDto.Read;
import com.tani.demo.entity.Cart;

@Service
public class CartService { //궁금한게 생겼다, 리턴타입을 왜 저렇게 받아오는지 모르겠다.  다들 왜 리턴타입이 저모양이지 카트클래스 리턴타입 이해불가 띠용 
	@Autowired
	CartDao cartDao;
	
	// 장바구니 추가
		public String AddCart(CartDto.AddCart dto) {
			
			// 장바구니에 동일한상품이 있다면 새로추가 하는 게 아니라 수량을 변경한다 
			if(cartDao.checkCart(dto.getCode(), dto.getUsername())) {
				
				// 수량변경
				cartDao.cartPlus(dto.getCode(), dto.getCartNum(), dto.getUsername());
				return null;
			}
				// 카트 수량추가	
				Cart cart = dto.toEntity();
				cartDao.addCart(cart);
				return null;
	}
		
		
	// 장바구니 리스트 읽어오기
		public List<CartDto.Read> listCart(String username) {
			
			List<Read> cart = cartDao.listCart(username);
			
			return cart;
		}
		
	// 장바구니 삭제 (선택항목 리스트를 하나씩 삭제할 예정)
		public void cartDelete(CartDto.delete list) {	//deleteDto 는 삭제항목을 배열(복수)로 받아왔음
			for(int i = 0; i<list.getCartNo().size(); i++) {	//리스트를 size만큼 돌리면서 삭제
				
				cartDao.cartDelete(list.getUsername(), list.getCartNo().get(i));	
			}
		}
		
		
		// 수량변경 
		public List<CartDto.Read> UpdateCount(CartDto.cartCountUpdate dto) {		
			
			if(dto.getCountVal()) { 							// true 일 경우 + false 일 경우 -
				cartDao.cartNumPlusCount(dto.getCartNo()); 		// 카트수량추가 Dao 
				return cartDao.listCart(dto.getUsername());		// 카트페이지 다시띄우기 
			}
			else {
				// 1아래로 감소 불가
				if(cartDao.getCount(dto.getCartNo()).get()>1) { //1보다 클경우에만 
					cartDao.cartNumMinusCount(dto.getCartNo());	// 마이너스 해라  
					 cartDao.listCart(dto.getUsername());		//카트페이지 다시띄워라
				}
				return cartDao.listCart(dto.getUsername());		//1이면 그냥 페이지 띄워라 (마이너스못하게막음)
			}	
		}
		
		
}
