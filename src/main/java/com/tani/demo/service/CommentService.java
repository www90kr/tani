package com.tani.demo.service;

import java.util.*;

import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.tani.demo.dao.*;
import com.tani.demo.dto.*;
import com.tani.demo.dto.CommentDto.*;
import com.tani.demo.entity.*;
import com.tani.demo.exception.*;

@Service
public class CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private BoardDao boardDao;
	
	public List<CommentDto.Read> write(CommentDto.Write dto, String loginId) {
		commentDao.save(dto.toEntity().addWriter(loginId));
		boardDao.update(Board.builder().bno(dto.getBno()).commentCnt(1).build());
		return commentDao.findByBno(dto.getBno());
	}

	public List<CommentDto.Read> delete(CommentDto.Delete dto, String loginId) {
		String writer = commentDao.findWriterById(dto.getCno()).orElseThrow(CommentNotFoundException::new);
		if(!writer.equals(loginId))
			throw new JobFailException("삭제할 수 없습니다");
		commentDao.deleteByCno(dto.getCno());
		return commentDao.findByBno(dto.getBno());
	}

}
