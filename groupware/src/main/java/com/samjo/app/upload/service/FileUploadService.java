package com.samjo.app.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	
	public void uploadFileInfo(MultipartFile[] uploadFiles, String empId, Integer docNo);
	
	public String makeFileFolder(String ftype);
	public String setFilePath(String uploadFileName);

}
