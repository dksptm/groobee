package com.samjo.app;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lowagie.text.DocumentException;
import com.samjo.app.common.util.PdfRender;

//@SpringBootTest
public class PdfTest {
	
	//@Autowired
	PdfRender pdf = new PdfRender();
	
	@Test
	public void pdftest() {
		String html = "<html><h1>hello</h1><p style='border:1px solid black'>3..</p></html>";
		try {
			pdf.generatePdfFromHtml(html);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
