package com.samjo.app.ct.mapper;

import java.util.List;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.solmodule.service.ModuleVO;

public interface CtMapper {

	//계약 전체조회
	public List<CtVO> selectCtAll(SearchVO searchVO);
	//계약 페이징
	public int ctCount(SearchVO searchVO);
	//계약 상세조회
	public CtVO ctInfo(int CtNo);
	//계약 상세조회-이용중 모듈목록
	public List<ModuleVO> selectModList(int CtNo);
	//계약 상세조회-이용중 계정목록
	public List<EmpVO> selectEmpList(int CtNo);
	//계약 상세조회-계약변경이력
	
}
