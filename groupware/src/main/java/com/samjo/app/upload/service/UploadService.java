package com.samjo.app.upload.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	
	public List<String> uploadFile(MultipartFile[] uploadFiles); 
	public String makeFolder(String ftype);
	public String setImagePath(String uploadFileName);
}
