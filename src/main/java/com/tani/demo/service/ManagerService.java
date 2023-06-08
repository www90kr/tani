package com.tani.demo.service;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tani.demo.dao.ManagerDao;
import com.tani.demo.dto.ManagerDto;
import com.tani.demo.dto.ManagerDto.Read;
import com.tani.demo.entity.Manager;
import com.tani.demo.exception.JobFailException;
import com.tani.demo.exception.MemberNotFoundException;
import com.tani.demo.util.MailUtil;

import lombok.Data;

@Data
@Service
public class ManagerService {
	@Autowired
	private ManagerDao dao;
	@Autowired
	private MailUtil mailUtil;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Boolean idAvailable(String username) {
		return (dao.existsBysId(username));
	}

	// 이메일 사용여부 확인
	public Boolean emailAvailable(String semail) {
		return (dao.existsBysEmail(semail));
	}

	// 닉네임 사용여부 확인
	public Boolean businessNoAvailable(Integer businessNo) {
		return (dao.existsByBusinessNo(businessNo));
	}

	// 연락처 사용여부 확인
	public Boolean telAvailable(Integer stel) {
		return (dao.existsBysTel(stel));
	}

	// 회원 가입
	public void join(ManagerDto.Join dto) {
		if (dao.existsBysId(dto.getUsername()))
			throw new JobFailException("사용중인 아이디입니다");
		if (dao.existsBysEmail(dto.getSemail()))
			throw new JobFailException("사용중인 이메일입니다");
		if (dao.existsBysTel(dto.getStel()))
			throw new JobFailException("사용중인 연락처입니다");
		if (dao.existsByBusinessNo(dto.getBusinessNo()))
			throw new JobFailException("사용중인 사업자번호입니다");

		Manager manager = dto.toEntity();
		// MultipartFile profile = dto.getProfile();

//		String profileName = "default.jpg";
//		// 프로필 사진이 있으면 저장하고 변경
//		if (profile != null && profile.isEmpty() == false) {
//			// 폴더명, 파일명으로 빈 파일을 생성한다
//			File file = new File(profileFolder, profile.getOriginalFilename());
//			try {
//				profile.transferTo(file); // ?
//				profileName = profile.getOriginalFilename();
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(manager.getSpassword());
		manager.addJoinInfo(encodedPassword);
		dao.save(manager);
	}

	// 아이디 찾기
	public Manager findBysId(ManagerDto.FindBysId dto) {
		Manager manager = dao.findBysId(dto.getSemail(), dto.getSname()).orElseThrow(() -> new MemberNotFoundException());
		return manager;
		// mailUtil.sendFindIdMail("admin@icia.com", dto.getEmail(), member.getId());

	}

	// -> 아이디찾기 메일로 전송?
	// 이메일, 아이디 입력 -> 임시비밀번호 전송
	// 비밀번호 리셋
	// 아이디로 검색 -> 없으면 MemberNotFoundException
	// 이메일을 확인 -> 틀리면 MemberNotFoundException
	// 20글자 임시비밀번호 생성 -> 암호화 -> 비번 변경 -> 메일 발송
	public void resetPassword(ManagerDto.ResetPassword dto) {
		Manager manager = dao.read(dto.getUsername()).orElseThrow(() -> new MemberNotFoundException());
		if (manager.getSemail().equals(dto.getSemail()) == false)
			throw new MemberNotFoundException();
		String newPassword = RandomStringUtils.randomAlphanumeric(20);
		dao.resetpw(Manager.builder().username(dto.getUsername()).spassword(passwordEncoder.encode(newPassword)).build());
		mailUtil.sendResetPasswordMail("admin@icia.com", dto.getSemail(), newPassword);

	}

	// 비밀번호 변경
	// 아이디로 검색 -> 없으면 MemberNotFoundException
	// 비밀번호를 맞춰본다 -> passwordEncoder.matches() -> 실패하면 예외처리
	// 새비밀번호를 암호화 후 저장
	public void changePassword(ManagerDto.ChangePassword dto, String username) {
		Manager manager = dao.read(username).orElseThrow(() -> new MemberNotFoundException());
		String encodedPassword = manager.getSpassword();
		if (passwordEncoder.matches(dto.getSpassword(), encodedPassword) == false)
			throw new JobFailException("비밀번호를 변경하지 못했습니다");
		dao.changepw(Manager.builder().username(username).spassword(passwordEncoder.encode(dto.getNewPassword())).build());
	}

	// 정보읽기
	public Read read(String username) {
		// 메소드 참조
		Manager manager = dao.read(username).get(); // 내정보 찾아봐야되니까 아이디에 맞는 정보 가져옴
		// .orElseThrow(MemberNotFoundException::new);
		ManagerDto.Read dto = manager.toDto();
		// Long days = ChronoUnit.DAYS.between(dto.getJoinday(), LocalDate.now());//
		// 가입일, 오늘날짜 사이값을 days라고 하고 dto에 극 값을
		// 집어넣음 (날짜값-날짜값 계산)
		// dto.setDays(days);
		// dto.setProfile(profilePath + dto.getProfile());// 파일+파일이름
		return dto;
	}

	// 정보 변경 - 이메일, 프사 변경
	// 이메일과 프사가 모두 비었으면 작업 중지
	// 프사만 비었으면 이메일만 변경
	// 이메일만 비었으면 프사만 변경
	// 이메일, 프사 모두 들었으면 모두 변경

	// 1. 이메일, 프사 모두 비었으면 작업 중지
	// 2. 아이디로 검색 -> 없으면 MemberNotFoundException
	// 3. String newEmail -> 이메일을 변경하면 저장, 안하면 기존 이메일 저장
	// 4. 프사 삭제 저장 후 저장
//	public Integer update(Update dto, String bname, String bemail) {
//		String newEmail = dto.getBemail();
//		if (newEmail == null)
//			throw new JobFailException("변경할 값이 없습니다");
//		Buyer buyer = dao.findBybId(bemail, bname).orElseThrow(MemberNotFoundException::new);
//		if (newEmail == null)
//			newEmail = buyer.getBemail();
//		return dao.update(Buyer.builder().bemail(bemail).bname(bname).build());
//	}



	// 회원정보 출력
	public Optional<ManagerDto.Read> readManager(String username) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Optional<ManagerDto.Read> dto = dao.ManagerInforRead(username);
		return dao.ManagerInforRead(username);
	}

	// 회원 탈퇴
	// findById -> 없으면 MemberNotFoundException해도 되는데...
	// existsById -> resultType이 true/false인 예제
	public Integer resign(String username) {
		if (dao.existsBysId(username) == false)
			throw new MemberNotFoundException();
		return dao.deleteBysId(username);
	}

}
