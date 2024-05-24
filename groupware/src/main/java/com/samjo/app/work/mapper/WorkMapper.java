package com.samjo.app.work.mapper;

import java.util.List;

import com.samjo.app.work.service.WorkManagerSearchVO;
import com.samjo.app.work.service.WorkManagerVO;
import com.samjo.app.work.service.WorkSearchVO;
import com.samjo.app.work.service.WorkVO;

public interface WorkMapper {
	
	// 전체 페이지 수
	public int workcount();
	public int managercount();
	
	// 근태관리 전체출퇴근조회
	public List<WorkVO> selectAllList(WorkSearchVO worksearchVO);
	// 출근 업데이트
	public int workin();
	// 퇴근 업데이트
	public int workout();
	// 출근등록
	public int insertWork(WorkVO workVo);
	
	// 관리자 페이지 전체조회
	public List<WorkManagerVO> managerWorkList(WorkManagerSearchVO workmanagersearchVO);
	// 페이지 상세 조회
	public WorkVO selectWork(WorkVO workVO);
	// 관리자 페이지 수정
	public int updateWork(WorkVO workVO);
	
	
	
	
	
	

	
}
