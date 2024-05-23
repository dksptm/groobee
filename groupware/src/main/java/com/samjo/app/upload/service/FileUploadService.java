package com.samjo.app.upload.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.approval.service.DocFileVO;

public interface FileUploadService {
	
	public List<Map<String, Object>> uploadFileInfo(MultipartFile[] uploadFiles, String empId, Integer docNo);
	
	public String makeFileFolder(String ftype);
	public String setFilePath(String uploadFileName);
	
	public boolean deleteFileInfo(List<String> fileSaveNames, int count);

}
