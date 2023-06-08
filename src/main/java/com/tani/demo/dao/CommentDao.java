package com.tani.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.tani.demo.entity.bComment;
import com.tani.demo.dto.CommentDto;

@Mapper
public interface CommentDao {
	// 글번호로 댓글 삭제
	@Delete("delete from bcomment where bno=#{bno}")
	public Integer deleteByBno(Integer bno);
	
	// 댓글 쓰기
	@SelectKey(statement="select nvl(max(cno), 0)+1 from bcomment",
		resultType=Integer.class, keyProperty="cno", before=true)
	@Insert("insert into bcomment(cno,cContent,username,bno,cWriteTime) "
			+ "values(#{cno},#{cContent},#{username},#{bno},sysdate)")
	public Integer save(bComment comment);
	
	// 글번호로 댓글 출력
	@Select("select cno,cContent,username,cWriteTime from bcomment where bno=#{bno}")
	public List<CommentDto.Read> findByBno(Integer bno); 
	
	// 글쓴이 확인
	@Select("select username from bcomment where cno=#{cno} and rownum<=1")
	public Optional<String> findWriterById(Integer cno);
	
	// 댓글번호로 댓글 삭제
	@Delete("delete from bcomment where cno=#{cno}")
	public Integer deleteByCno(Integer cno);
}
