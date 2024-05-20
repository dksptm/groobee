package com.samjo.app.work.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.work.mapper.WorkMapper;
import com.samjo.app.work.service.WorkSearchVO;
import com.samjo.app.work.service.WorkService;
import com.samjo.app.work.service.WorkVO;

@Service
public class WorkServiceImpl implements WorkService{
	
	@Autowired
	WorkMapper workMapper;
	// 전체 리스트
	@Override
	public List<WorkVO> workList(WorkSearchVO worksearchVO) {
		return workMapper.selectAllList(worksearchVO);
	}
	
	// 전체 데이터 수
	@Override
	public int workcount() {
		return workMapper.workcount();
	}
	
	// 출근 버튼
	@Override
	public Date workin() {
		return null;
	}
	// 퇴근 버튼
	@Override
	public Date workout() {
		return null;
	}
	// 출근 등록
	@Override
	public WorkVO insertWork(WorkVO workVO) {
		return null;
	}
	
	
	// 관리자 페이지 전체조회
	@Override
	public List<WorkVO> managerWorkList(WorkSearchVO worksearchVO) {
		return null;
	}
	// 관리자 페이지 상세조회
	@Override
	public WorkVO selectWork(WorkVO workVO) {
		return null;
	}
	// 관리자 페이지 수정
	@Override
	public int updateWork(WorkVO workVO) {
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
