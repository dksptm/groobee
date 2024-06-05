package com.samjo.app.cust.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.cust.service.CustVO;

public interface CustMapper {

	//고객사 전체조회
	public List<CustVO> selectCustAll();
	
	//고객사 등록
	public int insertCust(CustVO custVO);
	//고객사 단건조회
	public CustVO selectCustInfo(@Param("cno") String custNo);

}
