package com.samjo.app.ct.mapper;

import java.util.Date;
import java.util.List;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.ct.service.CtVO;
import com.samjo.app.cust.service.CustVO;
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
	public List<EmpVO> selectEmpList(String custNo);
	//계약 상세조회-계약변경이력
	public List<CtVO> selectCtHist(int CtNo);
	//계약 상세조회-계약변경이력(모듈)
	public List<ModuleVO> selectModHist(Date chgDt);
	//계약 수정-모듈목록조회
	public List<ModuleVO> selecetModAll();
	//계약 수정처리
	public int ctUpdate(CtVO ctVO);
	//계약 이력등록
	public int ctHist(int ctNo);
	//신규사용 모듈 등록
	public int useModInsert(ModuleVO modVO);
	//사용종료 모듈 갱신
	public int useModUpdate(int ctNo);
	//계약 신규등록
	public int ctInsert(CtVO ctVO);
	//계약가능 고객사조회
	public List<CustVO> selectCustAble();
	
}
