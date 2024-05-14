package com.samjo.app.download.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.samjo.app.download.service.DownloadService;

@RestController
public class DownloadController {

	@Autowired
	DownloadService downloadservice;

	@GetMapping("/download/{type}/{format}/{year}/{month}/{day}/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Object> download(@PathVariable String type, 
			@PathVariable String format,
			@PathVariable String year,
			@PathVariable String month,
			@PathVariable String day,
			@PathVariable String fileName) {
		System.out.println("fileName: "+ fileName);
		return downloadservice.downloadFile(type, format, year, month, day, fileName);
	}
}
