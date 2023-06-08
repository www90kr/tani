package com.tani.demo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Cart {
	private Integer cartNo;
	private Integer cartNum;

	private String username;
	private Integer code;
}