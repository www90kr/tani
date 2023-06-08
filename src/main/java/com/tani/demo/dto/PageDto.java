package com.tani.demo.dto;

import java.util.Collection;

import com.tani.demo.dto.AccDto.AccList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class PageDto {


      private Integer pageno;			//총페이지수
      private Integer pagesize;			//1페이지당 담을 글의 개수
      private Integer totalcount;		//총 글의개수
      private Collection<AccList> accListPage;			//페이징을 할 글 리스트Dto 
      
      //페이징 방법 설명
      //큰 틀에는 페이지Dto가 있다 > 이 페이지 Dto 안에 글리스트를 담아서 얘를 페이징할것이다
      //내가 띄우고 싶은 리스트A를 페이징 하고싶을때 준비물은
      //1.pageno , 2.pagesize , 3.totalcount 
      
   


}