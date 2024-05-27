package com.samjo.app.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Component
@RequiredArgsConstructor
public class PdfRender {
	
	public static byte[] generatePdfFromHtml(String html)
            throws DocumentException, IOException {
        
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //renderer 설정
        ITextRenderer renderer = new ITextRenderer();
        
        renderer.getFontResolver();
        renderer.setDocumentFromString(html);
        renderer.layout();
		// PDF 만들어준다.
        
        //String outputFolder = "C:/upload" + File.separator + "thymeleaf.pdf";
        //OutputStream outputStream = new FileOutputStream(outputFolder);
        
        renderer.createPDF(outputStream);
        
        outputStream.close();
        // outputStream 으로 리턴후 S3로 파일업로드를 stream 형태로 올린다.
        return outputStream.toByteArray();
    }
	
}
