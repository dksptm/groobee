package com.samjo.app.work.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.work.mapper.WorkMapper;
import com.samjo.app.work.service.WorkPageDTO;
import com.samjo.app.work.service.WorkService;
import com.samjo.app.work.service.WorkVO;

@Service
public class WorkServiceImpl implements WorkService{
	
	@Autowired
	WorkMapper workMapper;

	@Override
	public List<WorkVO> workList() {
		return workMapper.selectAllList();
	}
	// 전체 데이터 수
	@Override
	public int workcount() {
		return workMapper.workcount();
	}
	// 조회 조건
	@Override
	public WorkPageDTO page(WorkPageDTO workPageDTO) {
		return workMapper.page(workPageDTO);
	}

	
	
	
	
	
	
	
	
	
	
}
