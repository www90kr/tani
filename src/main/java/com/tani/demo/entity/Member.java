package com.tani.demo.entity;

import java.time.LocalDate;

import com.tani.demo.dto.*;
import com.tani.demo.dto.MemberDto.Read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private String username; // 사용자 입력
	private String password; // 서버에서 암호화해서 추가
	private String email;
	private String nickname;
	private String name;
	private Integer tel;
	private String address;


	private LocalDate joinday; //ㄴ
	private Integer enabled;
	private String role;
	
	public void addJoinInfo( String encodedPassword) {
		this.password = encodedPassword;
	}
	
	public Read toDto() {
		return MemberDto.Read.builder().username(username).name(name).email(email).nickname(nickname).tel(tel)
				.build();
	}
	public void updateemail(String email) {
		this.email = email;
	}
	
	public void updatenickname(String nickname) {
		this.nickname = nickname;
	}
	public void updatetel(Integer tel) {
		this.tel = tel;
	}
	public void updateaddress(String address) {
		this.address = address;
	}
	
	public void updatepassword(String password) {
		this.password= password;
	}
	


}
