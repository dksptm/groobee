package com.samjo.app.upload.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.upload.service.EmailFileUploadService;

@Service
public class EmailFileUploadServiceImpl implements EmailFileUploadService {
	
	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public List<Map<String, Object>> uploadFileInfo(MultipartFile[] uploadFiles) {
		List<Map<String, Object>> fileList = new ArrayList<>();

		for (MultipartFile uploadFile : uploadFiles) {
			
			// 파일사이즈 0이면(파일없으면) 멈추고 다시 for.
			if(uploadFile.getSize() == 0) {	
				continue;
			}
			
			// fileVO 필드값 넣을 map생성.
			Map<String, Object> map = new HashMap<>();
			
			// 파일의 타입구하기(타입별로 상위폴더 만들기 위함)
			String filetype = uploadFile.getContentType() + "/";
			
			String originalName = uploadFile.getOriginalFilename();
			
			// 각 필드값 구하기.
			String saveName;
			String uplName = originalName.substring(originalName.lastIndexOf("//") + 1);
			String fileExt = originalName.substring(originalName.lastIndexOf(".") + 1);;
			Long fileSize = uploadFile.getSize();

			// 타입>날짜 폴더 생성
			String folderPath = makeFileFolder(filetype);
			// UUID
			String uuid = UUID.randomUUID().toString();
			// 저장할 파일 이름 : 중간에 "_"를 이용하여 구분
			String uploadFileName = folderPath + File.separator + uuid + "_" + uplName;
			// 저장경로 포함 파일이름
			String saveFileName = uploadPath + File.separator + uploadFileName;
			// Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
			Path savePath = Paths.get(saveFileName);
			//System.out.println("savePath : " + savePath);
			
			try {
				// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
				// savePath에 파일저장.
				uploadFile.transferTo(savePath);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			// 필드 saveName 구하기.
			saveName = setFilePath(uploadFileName);
			
			// emailfile 클래스에 넣기.
			map.put("fileExt", fileExt);
			map.put("fileSize", fileSize);
			map.put("uplName", uplName);
			map.put("saveName", saveName);

			fileList.add(map);
			
		}
		
		return fileList;
		
	}
	
	@Override
	public String makeFileFolder(String ftype) {
		// LocalDate를 구하기.
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// 타입별(ftype) 문자열 + LocalDate를 문자열로 포멧
		String folderPath = ftype + str.replace("/", File.separator);
		// 업로드패스 + 폴더패스 결합한 경로를 가지는 File 인스턴스 생성.
		File uploadPathFoler = new File(uploadPath, folderPath);
		// 실제 폴더만들기.
		if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs(); 
		}

		return folderPath;
	}

	@Override
	public String setFilePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}


}
