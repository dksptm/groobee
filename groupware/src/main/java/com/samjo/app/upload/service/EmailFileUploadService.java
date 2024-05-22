package com.samjo.app.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface EmailFileUploadService {
	
	public void uploadFileInfo(MultipartFile[] uploadFiles, String empId, String senEmailNo);
	
	public String makeFileFolder(String ftype);
	public String setFilePath(String uploadFileName);

}
