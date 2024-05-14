package com.samjo.app.approval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.approval.service.AprVO;

public interface AprMapper {
	// 결재자등록(DocService에서 사용).
	public int insertApr(AprVO aprVO);
	// 결재자 리스트.
	public List<AprVO> selectDocApr(@Param("dno")Integer docNo);
	// 상신하기(프로시저는 void사용).
	public void updateMyApr(AprVO aprVO);
	// 결재하기.
	public void updateAprOk(AprVO aprVO);
	// 반려하기.
	public void updateAprNg(AprVO aprVO);
}
