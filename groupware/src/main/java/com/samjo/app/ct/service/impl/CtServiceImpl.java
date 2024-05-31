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
		List<CtVO> cthist = ctMapper.selectCtHist(ctNO);
		for(CtVO ctvo : cthist) {
			ctvo.setModHist(ctMapper.selectModHist(ctvo.getChgDt()));
		}
		ctVO = ctMapper.ctInfo(ctNO);
		ctVO.setModList(ctMapper.selectModList(ctNO));
		ctVO.setEmpList(ctMapper.selectEmpList(ctVO.getCustNo()));
		ctVO.setCtList(cthist);
		return ctVO; 
	}

	//모듈조회
	@Override
	public List<ModuleVO> modList() {
		return ctMapper.selecetModAll();
	}
	
	//계약 수정처리
	@Override
	public Map<String, Object> ctUpdate(CtVO ctVO, String[] modIds) {
		//계약서 파일등록 처리
		if(ctVO.getUploadfile() != null) {
			List<String> filePath = uploadservice.uploadFile(ctVO.getUploadfile());
			for(String file : filePath) {
		        String[] resultAry = file.split(":::");
		        String filepath = resultAry[resultAry.length - 1];
				ctVO.setCtFile(filepath);
			}
		}
		//계약이력 등록
		int result1 = ctMapper.ctHist(ctVO.getCtNo());
		//현재계약 수정
		int result2 = ctMapper.ctUpdate(ctVO);
		//모듈이력 등록
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

	//계약 등록처리
	@Override
	public Map<String, Object> ctInsert(CtVO ctVO, String[] modIds) {
		//계약서 파일등록 처리
		if(ctVO.getUploadfile() != null) {
			List<String> filePath = uploadservice.uploadFile(ctVO.getUploadfile());
			for(String file : filePath) {
		        String[] resultAry = file.split(":::");
		        String filepath = resultAry[resultAry.length - 1];
				ctVO.setCtFile(filepath);
			}
		}
		
		
		//사용모듈 등록
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
