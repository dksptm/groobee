package com.samjo.app.solmodule.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.samjo.app.approval.service.TempVO;
import com.samjo.app.cust.service.CustVO;
import com.samjo.app.solmodule.mapper.SolModMapper;
import com.samjo.app.solmodule.service.ModuleService;
import com.samjo.app.solmodule.service.ModuleVO;

@Service
public class ModuleServiceImpl implements ModuleService{

	
	SolModMapper solmodMapper;
	
	@Autowired
	public ModuleServiceImpl(SolModMapper solmodMapper) {
		this.solmodMapper = solmodMapper;
	}
	
	@Override
	public List<ModuleVO> modList() {
		// TODO Auto-generated method stub
		return solmodMapper.selectModAll();
	}

	@Override
	public List<CustVO> custList() {
		// TODO Auto-generated method stub
		return solmodMapper.selectCustAll();
	}

	@Override
	public String tempInsert(TempVO tempVO) {
		int result = solmodMapper.inserTemp(tempVO);
		if(result == 1) {
			return tempVO.getTempNo();
		}else {
			return "INSERT FAIL";
		}
	}

	@Override
	public void saveImg(String binaryData) {
		FileOutputStream stream = null;
		try{
			System.out.println("binary file   "  + binaryData);
			if(binaryData == null || binaryData.trim().equals("")) {
			    throw new Exception();
			}
			binaryData = binaryData.replaceAll("data:image/png;base64,", "");
			byte[] file = Base64.decodeBase64(binaryData);
			String fileName=  UUID.randomUUID().toString() + "_tempPreview";
			
			stream = new FileOutputStream("C:/uploadTest2/"+fileName+".png");
			stream.write(file);
			stream.close();
			System.out.println("캡처 저장");
		    
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("에러 발생");
		}finally{
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
