package com.samjo.app.ct.service;

import java.util.List;

import com.samjo.app.common.service.SearchVO;

public interface CtService {

	//계약 전체조회
	public List<CtVO> ctList(SearchVO searchVO);
	//계약 페이징
	public int count(SearchVO searchVO);
}
