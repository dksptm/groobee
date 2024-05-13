package com.samjo.app.work.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.work.mapper.WorkMapper;
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

	@Override
	public WorkVO getWorkId() {
		return workMapper.getWork();
	}
	
	@Override
	public int insertwork(WorkVO workVO) {
		return workMapper.insertwork(workVO);
	}
	
	
	
	
	
	
	
	
	
	
}
