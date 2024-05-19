package com.samjo.app.approval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.approval.service.TempVO;

public interface TempMapper {
	// 템플릿 목록 가져오기.
	public List<TempVO> selectCustTemps();
	
	// 휴가 등록/수정/삭제/조회
	public int insertPto(TempVO pto);
	public int updatePto(TempVO pto);
	public int deletePto(@Param("dno")Integer docNo);
	public TempVO selectPto(@Param("dno")Integer docNo);
}
