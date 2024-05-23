package com.samjo.app.ct.mapper;

import java.util.List;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.ct.service.CtVO;

public interface CtMapper {

	//계약 전체조회
	public List<CtVO> selectCtAll(SearchVO searchVO);
	
	//계약 페이징
	public int ctCount();
}
