package com.samjo.app.upload.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface EmailFileUploadService {
	
	public List<Map<String, Object>> uploadFileInfo(MultipartFile[] uploadFiles);
	
	public String makeFileFolder(String ftype);
	public String setFilePath(String uploadFileName);
	
	//효주님은 이걸 쓰시던데. 나는 첨부파일 지울 이유가 없어서 비활성화
	//public int deleteFileInfo(List<String> fileSaveNames);
}
