package com.samjo.app.work.mapper;

import java.util.List;

import com.samjo.app.work.service.WorkPageDTO;
import com.samjo.app.work.service.WorkVO;

public interface WorkMapper {
	
	// 근태관리 전체출퇴근조회
	public List<WorkVO> selectAllList();
	
	// 전체 페이지 수
	public int workcount();
	
	// 조회 조건들
	public WorkPageDTO page(WorkPageDTO workPageDTO);
	
	
	
	

	
}
