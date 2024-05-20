package com.samjo.app.work.mapper;

import java.util.List;

import com.samjo.app.work.service.WorkSearchVO;
import com.samjo.app.work.service.WorkVO;

public interface WorkMapper {
	
	// 전체 페이지 수
	public int workcount();
	
	// 근태관리 전체출퇴근조회
	public List<WorkVO> selectAllList(WorkSearchVO worksearchVO);
	
	// 출근버튼
	public int workin();
	// 퇴근버튼
	public int workout();
	// 출근등록
	public int insertWork();
	// 관리자 페이지 전체조회
	public List<WorkVO> managerWorkList(WorkSearchVO worksearchVo);
	
	// 관리자 페이지 상세 조회
	public WorkVO selectWork(WorkVO workVO);
	// 관리자 페이지 수정
	public int updateWork(WorkVO workVO);
	
	
	
	

	
}
