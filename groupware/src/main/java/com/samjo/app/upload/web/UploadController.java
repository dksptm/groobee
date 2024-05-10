package com.samjo.app.upload.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.upload.service.UploadService;

@Controller
public class UploadController {
	
		@Autowired
		UploadService uploadservice;
	
		//@Value("${file.upload.path}")
		//private String uploadPath;

		@GetMapping("/upload") // classpath:/temploates/upload.html
		public String getUploadPage() {
			return "common/upload";
		}
		
//		파일 중복명 처리
		@PostMapping("/uploadsAjax")
		@ResponseBody
		public List<String> uploadFile(@RequestPart MultipartFile[] uploadFiles) {
		    return uploadservice.uploadFile(uploadFiles);
		}
		
}
