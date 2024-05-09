package com.samjo.app.work.mapper;

import java.util.List;

import com.samjo.app.work.service.WorkVO;

public interface WorkMapper {
	
	// 근태관리 전체출결조회
	public List<WorkVO> selectAllList();
	// 계정정보조회
	public WorkVO getWorkId();
	
}
