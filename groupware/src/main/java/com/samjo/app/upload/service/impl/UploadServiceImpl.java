package com.samjo.app.upload.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.upload.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public List<String> uploadFile(MultipartFile[] uploadFiles) {

		List<String> fileList = new ArrayList<>();

		for (MultipartFile uploadFile : uploadFiles) {
			String filetype = uploadFile.getContentType() + "/";
			
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);
			
			System.out.println("fileName : " + fileName);
			
			// 날짜 폴더 생성
			String folderPath = makeFolder(filetype);
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
			// DB에 해당 경로 저장

			// 1) 사용자가 업로드할 때 사용한 파일명
			// 2) 실제 서버에 업로드할 때 사용한 경로
			fileList.add(setImagePath(uploadFileName));
		}

		return fileList;
	}

	@Override
	public String makeFolder(String ftype) {
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
	public String setImagePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}

	@Override
	public String uploadHtml(HashMap<String, Object> map) {
		String text = (String)map.get("tags");
		String fileName = (String)map.get("fileName");
		String path = "text/html/";
		// 날짜 폴더 생성
		String folderPath = makeFolder(path);
		// UUID
		String uuid = UUID.randomUUID().toString();
		// 저장할 파일 이름 중간에 "_"를 이용하여 구분

		String uploadFileName = folderPath + File.separator + uuid + "_" + fileName + ".html";

		String saveName = uploadPath + File.separator + uploadFileName;

		File f1 = new File(saveName);
		/*// 파일이 존재하면 종료
		 * if (f1.exists()) {
			System.out.println("파일이 존재..."); 
			return null;
		}*/
		byte[] bytes = text.getBytes();
		try {
			FileOutputStream fos = new FileOutputStream(f1);
			// 객체생성방법 1.절대경로넘기기(fullpath) 2.파일객체넘기기 (f1)
			
			fos.write(bytes);

			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return uploadFileName;
	}

}
