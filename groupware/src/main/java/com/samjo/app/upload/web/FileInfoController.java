package com.samjo.app.upload.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.upload.service.FileUploadService;

@Controller
public class FileInfoController {

	@Autowired
	FileUploadService fileUploadService;
	
	@PostMapping("/fileUpload")
	@ResponseBody
	public void uploadFile(@RequestPart MultipartFile[] uploadFiles, String empId, Integer docNo) {
	    fileUploadService.uploadFileInfo(uploadFiles,empId,docNo);
	}
	
}
