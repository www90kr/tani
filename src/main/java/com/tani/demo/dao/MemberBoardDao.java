package com.tani.demo.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberBoardDao {
	@Select("select count(*) from member_board where username=#{username} and bno=#{bno} and rownum<=1")
	public Boolean existsById(Map<String, Object> map); //해당글에 기존에 좋아요를 눌렀는지 여부 확인 
	
	@Insert("insert into member_board values(#{username}, #{bno})")
	public Integer save(Map<String,Object> map);	//해당글에 좋아요추가하면서 멤버이름 저장해두기 (나중에조회해야함) 좋아요중복안되게
}
