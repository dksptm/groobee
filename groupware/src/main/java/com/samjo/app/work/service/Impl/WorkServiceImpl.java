package com.samjo.app.work.service.Impl;

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
	
	// 출근 등록
	@Override
	public int workin() {
		return workMapper.workin();
	}
	// 퇴근 등록	
	@Override
	public int workout() {
		return workMapper.workout();
	}
	// 테이블 등록
	@Override
	public int insertWork() {
		return workMapper.insertWork();
	}
	
	
	// 관리자 페이지 전체조회
	@Override
	public List<WorkVO> managerWorkList(WorkSearchVO worksearchVO) {
		return workMapper.managerWorkList(worksearchVO);
	}
	// 관리자 페이지 상세조회
	@Override
	public WorkVO selectWork(WorkVO workVO) {
		return workVO;
	}
	// 관리자 페이지 수정
	@Override
	public int updateWork(WorkVO workVO) {
		return workMapper.insertWork();
	}
	
	
	
	
	
	
	
	
	
	
	
}
