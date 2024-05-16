package com.samjo.app.upload.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileInfoService {
	
	// 파일이름, 파일확장자, 파일사이즈
	public List<String> uploadFileInfo(MultipartFile[] uploadFiles);
	
	//경로폴더 생성
	public String makeFileFolder(String ftype);
	public String setFilePath(String uploadFileName);

}
