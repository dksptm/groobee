package com.samjo.app.solmodule.service;

import java.util.List;

import com.samjo.app.approval.service.TempVO;
import com.samjo.app.cust.service.CustVO;


public interface ModuleService {

	//모듈 전체조회
	public List<ModuleVO> modList();
	
	//고객사 목록조회
	public List<CustVO> custList();
	
	//템플릿 등록처리
	public String tempInsert(TempVO tempVO);
}