package com.samjo.app.upload.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.upload.service.FileInfoService;

@Service
public class FileInfoServiceImpl implements FileInfoService {

	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public List<String> uploadFileInfo(MultipartFile[] uploadFiles) {

		List<String> fileList = new ArrayList<>();

		for (MultipartFile uploadFile : uploadFiles) {
			String filetype = uploadFile.getContentType() + "/";

			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);
			String fileSize = String.valueOf(uploadFile.getSize());

			System.out.println("fileName : " + fileName);

			// 날짜 폴더 생성
			String folderPath = makeFileFolder(filetype);
			// UUID
			String uuid = UUID.randomUUID().toString();
			// 저장할 파일 이름 중간에 "_"를 이용하여 구분

			String uploadFileName = folderPath + File.separator + uuid + "_" + fileName;

			String saveName = uploadPath + File.separator + uploadFileName;

			Path savePath = Paths.get(saveName);
			// Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
			System.out.println("path : " + saveName);
			try {
				uploadFile.transferTo(savePath);
				// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
			} catch (IOException e) {
				e.printStackTrace();
			}
			filetype = originalName.substring(originalName.lastIndexOf(".") + 1);
			
			String test = "";
			test = originalName + ":::" + filetype + ":::" + fileSize + ":::" + setFilePath(uploadFileName);
			
			// DB에 해당 경로 저장
			
			// 1) 사용자가 업로드할 때 사용한 파일명
			//fileList.add(originalName);
			// 2) 업로드된 파일 유형
			//fileList.add(filetype);
			// 3) 업로드된 파일 사이즈
			//fileList.add(fileSize);
			// 4) 실제 서버에 업로드할 때 사용한 경로
			//fileList.add(setImagePath(uploadFileName));
			fileList.add(test);
		}

		return fileList;
	}

	@Override
	public String makeFileFolder(String ftype) {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧
		String folderPath = ftype + str.replace("/", File.separator);

		File uploadPathFoler = new File(uploadPath, folderPath);
		// File newFile= new File(dir,"파일명");
		if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
			// 만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
			// mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
			// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
		}

		return folderPath;
	}

	@Override
	public String setFilePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}


}
