package com.tani.demo.entity;

import lombok.*;
import lombok.experimental.*;

@Data
@AllArgsConstructor
@Builder
@Accessors(chain=true)
public class Mail {
	private String from;			// 보내는 사람 이메일
	private String to;				// 받는 사람 이메일
	private String subject;			// 제목
	private String text;			// 내용
}
