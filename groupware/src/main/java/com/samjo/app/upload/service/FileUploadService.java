package com.samjo.app.upload.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	
	public List<Map<String, Object>> uploadFileInfo(MultipartFile[] uploadFiles);
	
	public String makeFileFolder(String ftype);
	public String setFilePath(String uploadFileName);
	
	public int deleteFileInfo(List<String> fileSaveNames);

}
