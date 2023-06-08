package com.tani.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tani.demo.dao.BoardDao;
import com.tani.demo.dao.CommentDao;
import com.tani.demo.dao.MemberBoardDao;
import com.tani.demo.dto.BoardDto;
import com.tani.demo.dto.BoardDto.boardPage;
import com.tani.demo.dto.MemberBoardDto;
import com.tani.demo.entity.Board;
import com.tani.demo.exception.BoardNotFoundException;
import com.tani.demo.exception.JobFailException;

@Service
public class BoardService {
	
	@Autowired
	BoardDao dao;
	
	@Autowired
	CommentDao commentdao;
	
	@Autowired
	private MemberBoardDao memberBoardDao;
	
	 private Integer pagesize=10;
	 
	 @Value("c:/upload")
	 private String imageFolder;

	 @Value("http://localhost:8087/anony/images/")
	 private String imagePath;
	
	// 글저장 
	public Board boardSave(BoardDto.boardSave dto, String username) {
		System.out.println(dto);
		System.out.println(username);
		
		Board board = dto.toEntity();
		board.addWriter(username);
		
		 MultipartFile photo = dto.getBPhoto();
	        if (photo != null & photo.isEmpty() == false) {
	            // 업로드한 MultipartFile을 c:/upload에 이동하자
	            File file = new File(imageFolder, photo.getOriginalFilename());
	            try {
	                photo.transferTo(file);
	            } catch (IllegalStateException | IOException e) {
	                e.printStackTrace();
	            }
	            board.setBPhoto(imagePath + photo.getOriginalFilename());
	        }
	        System.out.println(board);

	        dao.boardSave(board);
	        return board;
	    }
		
		
		

	
	// 글 페이징 
	public BoardDto.boardPage findAll(Integer pageno, String username) {
		Integer totalcount = dao.count(username);
		Integer countOfPage = (totalcount-1)/pagesize + 1;
		if(pageno>countOfPage)
			pageno=countOfPage;
		else if(pageno<0)
			pageno=-pageno;
		else if(pageno==0)
			pageno=1;
		
		Integer start = (pageno-1) * pagesize + 1;
		Integer end = start * pagesize - 1;
		
		Map<String,Object> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		map.put("username",username );
		return new boardPage(pageno,pagesize,totalcount,dao.findAll(map));
	}
	
	// 글 읽기 : 글이 없으면 409(BNFE). 글이 있고 글쓴이이면 조회수 증가
	public BoardDto.read read(Integer bno, String username) {
		BoardDto.read dto = dao.read(bno).orElseThrow(()->new BoardNotFoundException());
		if(username!=null && dto.getUsername().equals(username)==false) {
			dao.update(Board.builder().bno(bno).readCnt(1).build());
			dto.setReadCnt(dto.getReadCnt()+1);	
		}
		dto.setComments(commentdao.findByBno(bno));
		return dto;
	}
	
	// 글변경 : 실패 - 글이 없다(BoardNotFoundException), 글쓴이가 아니다(JobFailException)
	public Integer update(BoardDto.Update dto, String username) {
		String writer = dao.findWriterById(dto.getBno()).orElseThrow(()->new BoardNotFoundException());
		if(writer.equals(username)==false)
			throw new JobFailException("변경 권한이 없습니다");
		return dao.update(dto.toEntity());
	}
	
	// 글삭제 : 실패 - 글이 없다(BNFE), 글쓴이가 아니다(JFE)
	public Integer delete(Integer bno, String username) {
		String writer = dao.findWriterById(bno).orElseThrow(()->new BoardNotFoundException());
		if(writer.equals(username)==false)
			throw new JobFailException("삭제 권한이 없습니다");
		return dao.delete(bno);
	}
	

	// 좋아요 또는 싫어요
	public Integer goodOrBad(MemberBoardDto dto, String loginId) {
		String writer = dao.findWriterById(dto.getBno()).orElseThrow(()->new BoardNotFoundException());
		if(writer.equals(loginId))
			throw new JobFailException("좋아요/싫어요 권한이 없습니다");
		Map<String,Object> map = new HashMap<>();
		map.put("bno", dto.getBno());
		map.put("username", loginId);
		if(memberBoardDao.existsById(map)==true) {
			if(dto.getIsGood()==true)
				return dao.findGoodCntById(dto.getBno());
			return dao.findBadCntById(dto.getBno());
		} else {
			memberBoardDao.save(map);
			if(dto.getIsGood()==true) {
				dao.update(Board.builder().bno(dto.getBno()).goodCnt(1).build());
				return dao.findGoodCntById(dto.getBno());
			}
			dao.update(Board.builder().bno(dto.getBno()).badCnt(1).build());
			return dao.findBadCntById(dto.getBno());
		}
	}
}
