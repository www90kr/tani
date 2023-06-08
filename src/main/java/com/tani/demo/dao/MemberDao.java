package com.tani.demo.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.tani.demo.dto.MemberDto;
import com.tani.demo.entity.Manager;
import com.tani.demo.entity.Member;

@Mapper
public interface MemberDao {
	//아이디 중복검사
	public Boolean existsById(String username); 
	
	//이메일 중복검사
	public Boolean existsByemail(String email);
	
	//닉네임 중복검사
	public Boolean existsBynickname(String nickname);
	
	//연락처 중복검사
	public Boolean existsBytel(Integer tel);
	
	//DB에 정보 저장 (회원가입)
	public Integer save(Member Member);
	
	//이매일, 이름 입력 -> 아이디찾기
	public Optional<Member> findById(String email, String name); 
	
	//정보 수정
	public Integer update(Member Member);
	
	//임시비밀번호 수정(업데이트)
	public Integer resetpw(Member Member);
	
	//임시비밀번호 입력받아 비밀번호 변경
	public Integer changepw(Member Member);
	
	// 업데이트를 위한 전체내용 가져오기
	public Optional<Member> userdataall(String username);
	
	// 업데이트를 위한 전체내용 가져오기
	public Optional<Manager> admindataall(String username);

	//회원 삭제
	public Integer deleteById(String username);
	
	//내 정보 읽기
	public Optional<Member> read(String username);
								//입력될값
	// 회원 정보 출력(읽기)
	public Optional<MemberDto.Read> MemberInforRead(String username);
	
	//주문시 회원정보 가져오기 
	public Member MemberInfo(String username);
}
