package com.tani.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.tani.demo.entity.Manager;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ManagerDto {
	@Data
	public static class IdCheck {
		@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
	}
	
	@Data
	public static class EmailCheck {
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String semail;
	}
	
	@Data
	@Builder
	public static class Join {
//		@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
		
		@NotEmpty(message="이름은 필수입력입니다")
		private String sname;
		
		@NotEmpty(message="비밀번호는 필수입력입니다")
		private String spassword;
		
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String semail;
		
		
		private String businessName;
		private Integer businessNo;
		private Integer stel;
		private Integer enabled;
		private String role;
		
		
		public Manager toEntity() {
			return Manager.builder().username(username).spassword(spassword).sname(sname)
					.semail(semail).businessName(businessName).businessNo(businessNo).stel(stel).build();
		}
	}
	
	@Data
	public static class FindBysId {
		@NotEmpty(message="이메일는 필수입력입니다")
		@Email(message="잘못된 이메일 형식입니다")
		private String semail;
		private String sname;
	}
	
	@Data
	public static class ResetPassword {
		//@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
		
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String semail;
	}

	@Data
	public static class ChangePassword {	
		@NotEmpty(message="비밀번호는 필수입력입니다")
		private String spassword;
		
		@NotEmpty(message="새 비밀번호는 필수입력입니다")
		private String newPassword;
	}

	@Data
	@Builder
	public static class Read {
		private String username;
		private String sname;
		private String semail;
		private Integer stel;
		private String businessName;
		private Integer businessNo;
		private LocalDate sjoinday;
		private Long sdays;
		
	}

	@Data
	public static class Update {
		private String semail;
		private Integer stel;
		
	
	}
}
