package com.tani.demo.entity;

import java.time.LocalDate;

import com.tani.demo.dto.*;
import com.tani.demo.dto.ManagerDto.Read;

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
public class Manager {
	private String username; // 사용자 입력
	private String spassword; // 서버에서 암호화해서 추가
	private String semail;
	private String sname;
	private Integer stel;
	private String businessName;
	private Integer businessNo;
	
	//	private String bprofile;		//ㄴ	


	private LocalDate sjoinday; //ㄴ
	private Integer enabled;
	private String role;
	
	public void addJoinInfo( String encodedPassword) {
		this.spassword = encodedPassword;
	}
	
	public Read toDto() {
		return ManagerDto.Read.builder().username(username).sname(sname).semail(semail).businessName(businessName).businessNo(businessNo).sjoinday(sjoinday).stel(stel)
				.build();
	}
}
