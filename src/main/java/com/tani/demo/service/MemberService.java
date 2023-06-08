package com.tani.demo.service;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tani.demo.dao.MemberDao;
import com.tani.demo.dto.MemberDto;
import com.tani.demo.dto.MemberDto.FindById;
import com.tani.demo.dto.MemberDto.Read;
import com.tani.demo.dto.MemberDto.Update;
import com.tani.demo.entity.Member;
import com.tani.demo.exception.*;
import com.tani.demo.util.MailUtil;

import lombok.Data;

@Data
@Service
public class MemberService {
	@Autowired
	private MemberDao dao;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("c:/upload/profile")
    private String profileFolder;
    @Value("http://localhost:8087/profile/")
    private String profilePath;

    // 서블릿의 생명주기 : init() -> service() -> destroy()
    // 스프링의 생명주기 어노테이션 : @PostConstruct, @PreDestory

    // 아이디 사용여부 확인
    /*
     * public void idAvailable(String id) {
     * if (memberDao.existsById(id))
     * throw new JobFailException("사용중인 아이디입니다");
     * }
     */
	public Boolean idAvailable(String username) {
		return (dao.existsById(username));
	}

	// 이메일 사용여부 확인
	public Boolean emailAvailable(String email) {
		return (dao.existsByemail(email));
	}

	// 닉네임 사용여부 확인
	public Boolean nicknameAvailable(String nickname) {
		return (dao.existsBynickname(nickname));
	}

	// 연락처 사용여부 확인
	public Boolean telAvailable(Integer tel) {
		return (dao.existsBytel(tel));
	}

	// 회원 가입
	public void join(MemberDto.Join dto) {
		if (dao.existsById(dto.getUsername()))
			throw new JobFailException("사용중인 아이디입니다");
		if (dao.existsByemail(dto.getEmail()))
			throw new JobFailException("사용중인 이메일입니다");
		if (dao.existsBynickname(dto.getNickname()))
			throw new JobFailException("사용중인 닉네임입니다");

		Member Member = dto.toEntity();
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
		String encodedPassword = passwordEncoder.encode(Member.getPassword());
		Member.addJoinInfo(encodedPassword);
		dao.save(Member);
	}

	// 아이디 찾기
	public Member findById(MemberDto.FindById dto) {
		Member Member = dao.findById(dto.getEmail(), dto.getName()).orElseThrow(() -> new MemberNotFoundException());
		return Member;
		// mailUtil.sendFindIdMail("admin@icia.com", dto.getEmail(), member.getId());

	}

	// -> 아이디찾기 메일로 전송?
	// 이메일, 아이디 입력 -> 임시비밀번호 전송
	// 비밀번호 리셋
	// 아이디로 검색 -> 없으면 MemberNotFoundException
	// 이메일을 확인 -> 틀리면 MemberNotFoundException
	// 20글자 임시비밀번호 생성 -> 암호화 -> 비번 변경 -> 메일 발송
	public void resetPassword(MemberDto.ResetPassword dto) {
		Member Member = dao.read(dto.getUsername()).orElseThrow(() -> new MemberNotFoundException());
		if (Member.getEmail().equals(dto.getEmail()) == false)
			throw new MemberNotFoundException();
		String newPassword = RandomStringUtils.randomAlphanumeric(20);
		dao.resetpw(Member.builder().username(dto.getUsername()).password(passwordEncoder.encode(newPassword)).build());
		MailUtil.sendResetPasswordMail("admin@icia.com", dto.getEmail(), newPassword);

	}

	// 비밀번호 변경
	// 아이디로 검색 -> 없으면 MemberNotFoundException
	// 비밀번호를 맞춰본다 -> passwordEncoder.matches() -> 실패하면 예외처리
	// 새비밀번호를 암호화 후 저장
	public void changePassword(MemberDto.ChangePassword dto, String username) {
		Member Member = dao.read(username).orElseThrow(() -> new MemberNotFoundException());
		String encodedPassword = Member.getPassword();
		if (passwordEncoder.matches(dto.getPassword(), encodedPassword) == false)
			throw new JobFailException("비밀번호를 변경하지 못했습니다");
		dao.changepw(Member.builder().username(username).password(passwordEncoder.encode(dto.getNewpassword())).build());
	}

	// 정보읽기
	public Read read(String username) {
		// 메소드 참조
		Member Member = dao.read(username).get(); // 내정보 찾아봐야되니까 아이디에 맞는 정보 가져옴
		// .orElseThrow(MemberNotFoundException::new);
		MemberDto.Read dto = Member.toDto();
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
//		String newEmail = dto.getemail();
//		if (newEmail == null)
//			throw new JobFailException("변경할 값이 없습니다");
//		Member Member = dao.findBybId(bemail, bname).orElseThrow(MemberNotFoundException::new);
//		if (newEmail == null)
//			newEmail = Member.getemail();
//		return dao.update(Member.builder().bemail(bemail).bname(bname).build());
//	}

	public MemberDto.Read updateMember(MemberDto.Update dto, String username) {

		if (dto.getEmail() != null && dto.getAddress() == null && dto.getNickname() == null && dto.getTel() == null
				&& dto.getPassword() == null) {

			Member bdata = dao.userdataall(username).get();

			bdata.updateemail(dto.getEmail());

			dao.update(bdata);
			return dao.MemberInforRead(username).get();
		}

		if (dto.getAddress() != null && dto.getEmail() == null && dto.getNickname() == null && dto.getTel() == null
				&& dto.getPassword() == null) {

			Member bdata = dao.userdataall(username).get();

			bdata.updateaddress(dto.getAddress());

			dao.update(bdata);
			return dao.MemberInforRead(username).get();
		}

		if (dto.getNickname() != null && dto.getAddress() == null && dto.getEmail() == null && dto.getTel() == null
				&& dto.getPassword() == null) {

			Member bdata = dao.userdataall(username).get();

			bdata.updatenickname(dto.getNickname());
			System.out.println(bdata);
			dao.update(bdata);
			return dao.MemberInforRead(username).get();
		}

		if (dto.getTel() != null && dto.getNickname() == null && dto.getAddress() == null && dto.getEmail() == null
				&& dto.getPassword() == null) {

			Member bdata = dao.userdataall(username).get();

			bdata.updatetel(dto.getTel());

			dao.update(bdata);
			return dao.MemberInforRead(username).get();
		}

		if (dto.getPassword() != null && dto.getTel() == null && dto.getNickname() == null
				&& dto.getAddress() == null && dto.getEmail() == null) {

			Member bdata = dao.userdataall(username).get();

			String newpassword = passwordEncoder.encode(dto.getPassword());

			bdata.updatepassword(newpassword);

			dao.update(bdata);
			return dao.MemberInforRead(username).get();
		}
		return dao.MemberInforRead(username).get();
	}

	// 회원정보 출력
	public Optional<MemberDto.Read> readMember(String username) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Optional<MemberDto.Read> dto = dao.MemberInforRead(username);
		String nickname = dto.get().getNickname();
		return dao.MemberInforRead(username);
	}

	// 회원 탈퇴
	// findById -> 없으면 MemberNotFoundException해도 되는데...
	// existsById -> resultType이 true/false인 예제
	public Integer resign(String username) {
		if (dao.existsById(username) == false)
			throw new MemberNotFoundException();
		return dao.deleteById(username);
	}
	
	   //주문시 회원정보 가져오기 
	   public Member MemberInfo(String username) {
	      return dao.MemberInfo(username);
	   }
}
