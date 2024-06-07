package com.samjo.app.pay.mapper;

import java.util.Date;
import java.util.List;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.pay.service.PayVO;

public interface PayMapper {

	//결제 전체조회
	public List<PayVO> selectPayAll(SearchVO searchVO);
	//결제 페이징카운트
	public int payCount(SearchVO searchVO);
	//최초결제인증 등록
	public int fstPayInsert(PayVO payVO);
	//결제 등록
	public int payInsert(PayVO payVO);
	//결제 수정(갱신시)
	public int payReset(PayVO payVO);
	//고객사 결제 조회
	public List<PayVO> selectCustPayAll(SearchVO searchVO);
	public int custPayCount(String custNo);
	//고객사 결제예정일자 조회
	public Date payDay(String custNo);
	//결제 내역 갱신
	public int payUpdate(PayVO payVO);
	//결제 예정내역 조회
	public List<PayVO> selectConPay();
	//결제대기 내역조회
	public List<PayVO> selectWaitPay();
	//정기결제 취소
	public int cancelPay(int ctNo);
}
