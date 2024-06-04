package com.samjo.app.ct.service;

import java.util.List;
import java.util.Map;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.cust.service.CustVO;
import com.samjo.app.solmodule.service.ModuleVO;

public interface CtService {

	//계약 전체조회
	public List<CtVO> ctList(SearchVO searchVO);
	//계약 페이징
	public int count(SearchVO searchVO);
	//계약 상세조회
	public CtVO ctInfo(int ctNO);
	//모듈목록 모달조회
	public List<ModuleVO> modList();
	//계약 등록처리
	public Map<String, Object> ctInsert(CtVO ctVO, String[] modIds);
	//고객사 모달조회
	public List<CustVO> custList();
	//계약 수정처리
	public Map<String, Object> ctUpdate(CtVO ctVO, String[] modIds);
	//고객사 관리자 계약조회
	public List<CtVO> custCtList(String custNo);
	//고객사 정기결제 갱신
	public int ctPayCheck(CtVO ctVO);
}
