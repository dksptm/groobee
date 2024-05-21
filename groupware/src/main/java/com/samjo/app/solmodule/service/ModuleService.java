package com.samjo.app.solmodule.service;

import java.util.List;

import com.samjo.app.approval.service.TempVO;
import com.samjo.app.cust.service.CustVO;


public interface ModuleService {

	//모듈 전체조회
	public List<ModuleVO> modList();
	
	//템플릿 전체조회
	public List<TempVO> tempList();
	
	//고객사 목록조회
	public List<CustVO> custList();
	
	//템플릿 등록처리
	public String tempInsert(TempVO tempVO);
	
	//이미지생성
	public String saveImg(String binaryData);
	
	//템플릿 상세조회
	public TempVO tempInfo(String tempNo);
	
}