package com.samjo.app.pay.mapper;

import java.util.List;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.pay.service.PayVO;

public interface PayMapper {

	//결제 전체조회
	public List<PayVO> selectPayAll(SearchVO searchVO);
	//결제 페이징카운트
	public int payCount(SearchVO searchVO);
	
}
