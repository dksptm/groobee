package com.samjo.app.download.service.impl;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samjo.app.download.service.DownloadService;

@Service
public class DownloadServiceImpl implements DownloadService {

	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public ResponseEntity<Object> downloadFile(String type, String format, String year, String month, String day,
			String fileId) {

		//다운로드받을 파일 경로값
		String path = uploadPath + File.separator + type + File.separator + format + File.separator + year
				+ File.separator + month + File.separator + day + File.separator + fileId;

		try {
			Path filePath = Paths.get(path);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

			File file = new File(path);
			
			//파일명에서 uuid 제거
			String makeName = URLEncoder.encode(file.getName(),"UTF-8");
			String NameAry[] = makeName.split("_");
			String DownName = NameAry[1]; 
			
			//파일생성
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(DownName).build());

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}

}
