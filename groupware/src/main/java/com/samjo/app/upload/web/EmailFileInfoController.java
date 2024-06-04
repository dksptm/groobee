package com.samjo.app.upload.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.upload.service.EmailFileUploadService;

@Controller
public class EmailFileInfoController {

	@Autowired
	EmailFileUploadService emailFileUploadService;
	
	@PostMapping("cust/EmailFileUpload")
	@ResponseBody
	public void uploadFile(@RequestPart MultipartFile[] uploadFiles, String empId, String senEmailNo) {
	    emailFileUploadService.uploadFileInfo(uploadFiles);
	}
	
}
