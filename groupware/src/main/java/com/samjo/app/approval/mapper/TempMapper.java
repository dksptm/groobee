package com.samjo.app.approval.mapper;

import java.util.List;

import com.samjo.app.approval.service.TempVO;

public interface TempMapper {
	// 템플릿 목록 가져오기.
	public List<TempVO> selectCustTemps();
}
