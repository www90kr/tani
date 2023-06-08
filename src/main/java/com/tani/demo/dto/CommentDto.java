package com.tani.demo.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.tani.demo.dto.CommentDto;
import com.tani.demo.entity.bComment;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class CommentDto {
	@Data
	public static class Read {
		private Integer cno;
		private String cContent;
		private String username;
		@JsonFormat(pattern="yyyy-MM-dd")
		private LocalDateTime cWriteTime;
	}

	@Data
	@Builder
	public static class Write {
		@NotEmpty
		private String cContent;
		@NotNull
		private Integer bno;
		public bComment toEntity() {
			return bComment.builder().cContent(cContent).bno(bno).build();
		}
	}
	
	@Data
	public static class Delete {
		@NotNull
		private Integer cno;
		@NotNull
		private Integer bno;
	}
}