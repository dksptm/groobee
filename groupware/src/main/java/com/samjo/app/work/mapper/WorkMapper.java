package com.samjo.app.work.mapper;

import java.util.List;

import com.samjo.app.work.service.WorkSearchVO;
import com.samjo.app.work.service.WorkVO;

public interface WorkMapper {
	
	// 근태관리 전체출퇴근조회
	public List<WorkVO> selectAllList(WorkSearchVO worksearchVO);
	
	// 전체 페이지 수
	public int workcount();
	
	
	
	
	

	
}
