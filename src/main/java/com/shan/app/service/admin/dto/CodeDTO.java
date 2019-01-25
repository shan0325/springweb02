package com.shan.app.service.admin.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class CodeDTO {

	@Data
	public static class Create {

		@NotBlank(message = "코드를 입력해주세요.")
		private String code;
		
		@NotBlank(message = "코드명을 입력해주세요.")
		private String codeName;
		
		private String parentCode;
		
		private String codeDesc;
	}
	
	@Setter
	@Getter
	public static class Response {
		private Long id;
		private String code;
		private String codeName;
		private String parentCode;
		private String topCode;
		private String codeDesc;
		private Integer level;
		private String useYn;
		private Integer ord;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
		private LocalDateTime regDate;
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
		private LocalDateTime updateDate;
	}
	
	
}
