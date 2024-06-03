package com.samjo.app.pay.mapper;

import java.util.List;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.pay.service.PayVO;

public interface PayMapper {

	//결제 전체조회
	public List<PayVO> selectPayAll(SearchVO searchVO);
	//결제 페이징카운트
	public int payCount(SearchVO searchVO);
	//결제 등록
	public int payInsert(PayVO payVO);
	//결제 수정(갱신시)
	public int payReset(PayVO payVO);
}
