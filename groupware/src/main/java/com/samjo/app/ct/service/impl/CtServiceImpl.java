package com.samjo.app.ct.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.ct.mapper.CtMapper;
import com.samjo.app.ct.service.CtService;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.solmodule.service.ModuleVO;
import com.samjo.app.upload.service.UploadService;

@Service
public class CtServiceImpl implements CtService{

	@Value("${file.upload.path}")
	private String uploadPath;

	@Autowired
	CtMapper ctMapper;
	@Autowired
	UploadService uploadservice;
	
	//계약 전체조회
	@Override
	public List<CtVO> ctList(SearchVO searchVO) {
		return ctMapper.selectCtAll(searchVO);
	}

	//계약 페이징
	@Override
	public int count(SearchVO searchVO) {
		return ctMapper.ctCount(searchVO);
	}

	//계약 상세조회
	@Override
	public CtVO ctInfo(int ctNO) {
		CtVO ctVO = new CtVO();
		ctVO = ctMapper.ctInfo(ctNO);
		ctVO.setModList(ctMapper.selectModList(ctNO));
		ctVO.setEmpList(ctMapper.selectEmpList(ctVO.getCustNo()));
		ctVO.setCtList(ctMapper.selectCtHist(ctNO));
		return ctVO; 
	}

	@Override
	public List<ModuleVO> modList() {
		return ctMapper.selecetModAll();
	}

	@Override
	public Map<String, Object> ctUpdate(CtVO ctVO, String[] modIds) {
		if(ctVO.getUploadfile() != null) {
			List<String> filePath = uploadservice.uploadFile(ctVO.getUploadfile());
			for(String file : filePath) {
		        String[] resultAry = file.split(":::");
		        String filepath = resultAry[resultAry.length - 1];
				ctVO.setCtFile(filepath);
			}
		}
		int result1 = ctMapper.ctHist(ctVO.getCtNo());
		int result2 = ctMapper.ctUpdate(ctVO);
		
		ctMapper.useModUpdate(ctVO.getCtNo());
		for(String modId : modIds) {
			ModuleVO modVO = new ModuleVO();
			modVO.setModId(modId);
			modVO.setCtNo(ctVO.getCtNo());
			modVO.setCustNo(ctVO.getCustNo());
			modVO.setCustName(ctVO.getCustName());
			modVO.setUseStartDt(ctVO.getCtStartDt());
			modVO.setUseEndDt(ctVO.getCtEndDt());
			ctMapper.useModInsert(modVO);
		}
		return null;
	}

}
