package com.tani.demo.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.tani.demo.dto.MemberDto;
import com.tani.demo.dto.RestResponse;
import com.tani.demo.entity.Member;
import com.tani.demo.service.*;

@Controller
public class MemberController {

    @Autowired
    private MemberService service;

    // job1. 아이디 중복 확인 - 예외상황을 if문 처리
    @GetMapping(path = "/member/check/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> checkId(String username) {
        // service.idAvailable(id);
        // return ResponseEntity.ok(new RestResponse("OK", "사용할 수 있는 아이디입니다", null));

        if (service.idAvailable(username) == true) {
            return ResponseEntity.ok(new RestResponse("FAIL", "사용할 수 없는 아이디입니다", null));
        }
        return ResponseEntity.ok(new RestResponse("OK", "사용할 수 있는 아이디입니다", null));
    }

    // job2. 이메일 중복 확인
    @GetMapping(path = "/member/check/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> checkemail(String email) {

        if (service.emailAvailable(email) == true) {
            return ResponseEntity.ok(new RestResponse("FAIL", "사용할 수 없는 이메일입니다", null));
        }
        return ResponseEntity.ok(new RestResponse("OK", "사용할 수 있는 이메일입니다", null));
    }

    // job3. 닉네임 중복 확인
    @GetMapping(path = "/member/check/nickname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> checkNickname(String nickname) {

        if (service.nicknameAvailable(nickname) == true) {
            return ResponseEntity.ok(new RestResponse("FAIL", "사용할 수 없는 닉네임입니다", null));
        }
        return ResponseEntity.ok(new RestResponse("OK", "사용할 수 있는 닉네임입니다", null));
    }
    
	@GetMapping(path = "/member/check/tel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> checkTel(Integer tel) {

		if (service.telAvailable(tel) == true) {
			return ResponseEntity.ok(new RestResponse("FAIL", "이미 등록된 전화번호입니다", null));
		}
		return ResponseEntity.ok(new RestResponse("OK", "사용 가능한 전화번호입니다", null));
	}

    // job 4. 아이디찾기 페이지로 이동
    @GetMapping(path = "/member/findById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> findById(@Valid MemberDto.FindById dto, BindingResult bindingResult) {
        service.findById(dto);
        return ResponseEntity.ok(new RestResponse("OK", dto, "/member/findById"));
    }

    // job 5. 이메일, 이름을 입력받아 아이디 출력
    @GetMapping(path = "/member/find/username", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> findusername(@Valid MemberDto.FindById dto, BindingResult bindingResult) {
        service.findById(dto);
        String username = service.findById(dto).getUsername();
        return ResponseEntity.ok(new RestResponse("OK", username, null));
    }

    // job 7. 아이디와 이메일을 입력받아 임시비밀번호를 이메일로 보낸다
    @PatchMapping(path = "/member/reset/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> resetPassword(@Valid MemberDto.ResetPassword dto, BindingResult bindingResult) {
        System.out.println(dto);
        service.resetPassword(dto);
        return ResponseEntity.ok(new RestResponse("OK", "이메일로 임시비밀번호가 전송되었습니다", "/member/login"));
    }

    // job 8. 비밀번호 변경
    // @PreAuthorize("isAuthenticated()")
    @PatchMapping(path = "/member/new/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> changePassword(@Valid MemberDto.ChangePassword dto, BindingResult bindingResult,
            Principal principal) {
        service.changePassword(dto, principal.getName());
        return ResponseEntity.ok(new RestResponse("OK", "비밀번호가 변경되었습니다", "/member/login"));
    }
    
    
	// 정보 수정
	@PutMapping(value = "/member/update")
	public ResponseEntity<RestResponse> update(MemberDto.Update dto, Principal principal) {
		System.out.println(dto);
		service.updateMember(dto, principal.getName());
		return ResponseEntity.ok(new RestResponse("계정 정보 수정", service.readMember(principal.getName()).get(), null));
	}

    // 회원가입
    @PostMapping(value = "/member/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> join(MemberDto.Join dto) {
        service.join(dto);
        return ResponseEntity.ok(new RestResponse("OK", "가입 성공", null));
    }

	// 회원정보 출력
	@GetMapping(value = "/member/readmember", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> readBuyer(Principal principal) {
		MemberDto.Read dto = service.readMember(principal.getName()).get();
		return ResponseEntity.ok(new RestResponse("계정 정보", dto, null));
	}


    // job 10. 회원 탈퇴
    // 스프링 시큐리티로 로그아웃을 시킨다 - 이때 Authentication 객체 필요
    // Authentication - 인증 정보를 담은 객체
    // Principal - 로그인 아이디를 담고 있는 객체
    // - 비로그인일 때 Authentication은 anonymousUser란 아이디를 가진다
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(path = "/member/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse> resign(SecurityContextLogoutHandler handler, HttpServletRequest req,
            HttpServletResponse res, Authentication authentication) {
        service.resign(authentication.getName());
        handler.logout(req, res, authentication);
        return ResponseEntity.ok(new RestResponse("OK", "회원 정보를 삭제했습니다", "/anony/home"));
    }

}