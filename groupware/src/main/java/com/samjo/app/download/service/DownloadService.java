package com.samjo.app.download.service;

import org.springframework.http.ResponseEntity;

public interface DownloadService {

	public ResponseEntity<Object> downloadFile(String type, String format, String year, String month, String day, String fileId);
}
