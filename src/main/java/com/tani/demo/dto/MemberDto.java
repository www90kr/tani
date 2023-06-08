package com.tani.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.tani.demo.dto.MemberDto;
import com.tani.demo.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {
	@Data
	public static class IdCheck {
		@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
	}
	
	@Data
	public static class NicknameCheck {
		@NotEmpty(message="닉네임은 필수입력입니다")
		private String nickname;
	}
	
	@Data
	public static class EmailCheck {
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String email;
	}
	
	@Data
	public static class TelCheck {
		@Email
		@NotEmpty(message="연락처은 필수입력입니다")
		private Integer tel;
	}
	
	@Data
	@Builder
	public static class Join {
		@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
		
		@NotEmpty(message="이름은 필수입력입니다")
		private String name;
		
		@NotEmpty(message="비밀번호는 필수입력입니다")
		private String password;
		
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String email;
		
		@Size(min= 2, max = 10, message = "닉네임은 몇글자")
		@NotEmpty(message="닉네임은 필수입력입니다")
		private String nickname;
		
		@NotEmpty(message="연락처는 필수입력입니다")
		private Integer tel;
		
		@NotEmpty(message="주소는 필수입력입니다")
		private String address;
		
		private Integer enabled;
		private String role;
		
		
		public Member toEntity() {
			return Member.builder().username(username).password(password).name(name)
					.email(email).nickname(nickname).address(address).tel(tel).build();
		}
	}
	
	@Data
	public class FindById {
		@NotEmpty(message="이메일는 필수입력입니다")
		@Email(message="잘못된 이메일 형식입니다")
		private String email;
		private String name;
	}
	
	@Data
	public class ResetPassword {
		//@Pattern(regexp="^[A-Z0-9]{8,10}$", message = "아이디는 대문자와 숫자 8~10자입니다")
		@NotEmpty(message="아이디는 필수입력입니다")
		private String username;
		
		@Email
		@NotEmpty(message="이메일은 필수입력입니다")
		private String email;
	}

	@Data
	public class ChangePassword {	
		@NotEmpty(message="비밀번호는 필수입력입니다")
		private String password;
		
		@NotEmpty(message="새 비밀번호는 필수입력입니다")
		private String newpassword;
	}

	@Data
	@Builder
	public static class Read {
		private String username;
		private String name;
		private String email;
		private String nickname;
		private Integer tel;
		private String address;
		
	}

	@Data
	@Builder
	public static class Update {
		private String email;
		private Integer tel;
		private String password;
		private String address;
		private String nickname;
	
		public Member toEntity() {
			return Member.builder().email(email).address(address).nickname(nickname).password(password).tel(tel).build();		}
	}
	
	   //주문시 회원정보 가져오기
	   @Data
	   public class memberInfo{
	      private String username;   //회원코드
	      private String address;      //배송주소
	      private String name;      
	      private Integer tel;
	      private String email;
	   }
}
