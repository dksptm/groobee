package com.samjo.app.approval.web;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.samjo.app.approval.service.AprService;
import com.samjo.app.approval.service.AprVO;
import com.samjo.app.common.util.PdfRender;

@RestController
public class AprController {
	
	@Autowired
	AprService aprService;
	
	@Autowired
	PdfRender pdf;
	
	@PutMapping("cust/aprGo/{docNo}")
	public Map<String, Object> goToApr(@PathVariable Integer docNo, @RequestBody AprVO aprVO) { 
		return aprService.goToApr(aprVO);
	}
	
	@PutMapping("cust/aprOk/{docNo}")
	public Map<String, Object> aprOk(@PathVariable Integer docNo, @RequestBody AprVO aprVO) {
		return aprService.aprOk(aprVO);
	}
	
	@PutMapping("cust/aprNg/{docNo}")
	public Map<String, Object> aprNg(@PathVariable Integer docNo, @RequestBody AprVO aprVO) {
		return aprService.aprNg(aprVO);
	}
	
	@PostMapping("cust/docPDF/{docNo}")
	public ResponseEntity<byte[]> pdfTest(@PathVariable Integer docNo, String html) {

		byte[] result = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			result = PdfRender.generatePdfFromHtml(html);
			headers.setContentType(MediaType.APPLICATION_PDF);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(result, headers, HttpStatus.OK);
	}

}
