package com.samjo.app.upload.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
	
	//업로드 기능
	public List<String> uploadFile(MultipartFile[] uploadFiles);
	//경로폴더 생성
	public String makeFolder(String ftype);
	public String setImagePath(String uploadFileName);
	
	//직접입력 처리
	public String uploadHtml(HashMap<String, Object> map);
}
