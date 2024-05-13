package com.samjo.app.upload.web;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.upload.service.UploadService;

@Controller
public class UploadController {
	
		@Autowired
		UploadService uploadservice;

		/*
		 * @GetMapping("/upload") // classpath:/temploates/upload.html public String
		 * getUploadPage() { return "common/upload"; }
		 */
		
		/* 업로드 기능
		 * 입력값 : 업로드파일 
		 * 출력값 : 저장 경로
		 * 기능 : 업로드된 파일을 저장경로에 생성하고 중복명이 생기지않도록 임의의 값을 추가. */
		@PostMapping("/uploadsAjax")
		@ResponseBody
		public List<String> uploadFile(@RequestPart MultipartFile[] uploadFiles) {
		    return uploadservice.uploadFile(uploadFiles);
		}
		
		/* 직접입력 기능 
		 * 입력값 : 문자열
		 * 출력값 : 저장경로
		 * 기능 : 받은 문자열을 html 파일로 전환하여 생성하고 중복명 처리. */
		@PostMapping("/uploadsHtml")
		@ResponseBody
		public String uploadHtml(@RequestBody HashMap<String, Object> map) {
			return uploadservice.uploadHtml(map);
		}
		
}
