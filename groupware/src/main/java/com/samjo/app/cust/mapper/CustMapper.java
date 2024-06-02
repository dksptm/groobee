package com.samjo.app.cust.mapper;

import java.util.List;

import com.samjo.app.cust.service.CustVO;

public interface CustMapper {

	//고객사 전체조회
	public List<CustVO> selectCustAll();
}
