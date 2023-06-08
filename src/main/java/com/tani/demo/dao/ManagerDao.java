package com.tani.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.tani.demo.dto.ManagerDto;
import com.tani.demo.entity.*;
@Mapper
public interface ManagerDao {
	//아이디 중복검사
	public Boolean existsBysId(String username); 
	
	//이메일 중복검사
	public Boolean existsBysEmail(String semail);
	
	//연락처 중복검사
	public Boolean existsBysTel(Integer stel);
	
	//사업자번호 중복검사
	public Boolean existsByBusinessNo(Integer businessNo);
	
	//DB에 정보 저장 (회원가입)
	public Integer save(Manager manager);
	
	//이매일, 이름 입력 -> 아이디찾기
	public Optional<Manager> findBysId(String semail, String sname); 
	
	//정보 수정
	public Integer update(Manager manager);
	
	//임시비밀번호 수정(업데이트)
	public Integer resetpw(Manager manager);
	
	//임시비밀번호 입력받아 비밀번호 변경
	public Integer changepw(Manager manager);
	
	// 업데이트를 위한 전체내용 가져오기
	public Optional<Manager> userdataall(String username);

	//회원 삭제
	public Integer deleteBysId(String username);
	
	//내 정보 읽기
	public Optional<Manager> read(String username);
								//입력될값
	// 회원 정보 출력(읽기)
	public Optional<ManagerDto.Read> ManagerInforRead(String username);
	
	
}
