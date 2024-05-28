package com.samjo.app.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.project.mapper.ReguMapper;
import com.samjo.app.project.service.ProjectVO;
import com.samjo.app.project.service.ReguService;

@Service
public class ReguServiceImpl implements ReguService {

	ReguMapper reguMapper;
	
	@Autowired
	public ReguServiceImpl(ReguMapper reguMapper) {
		this.reguMapper = reguMapper;
	}
	
	// 처음 상시업무 등록.
	@Override
	public int reguStadInsert(ProjectVO regu) {
		int result = 0;
		
		result = reguMapper.insertRegu(regu);
		if(result != 1) {
			return -1;
		}
		
		result = 0;
		
		result = reguMapper.insertReguTaskEmp(regu);
		
		return result;
	}
	
	// 기존 상시업무 등록.
	@Override
	public int reguCommonInsert(ProjectVO regu) {
		System.out.println(regu);
		return reguMapper.insertReguTaskEmp(regu);
	}
	
	// 기존 상시업무목록 가져오기.
	@Override
	public List<ProjectVO> reguStadList(String custNo) {
		return reguMapper.selectReguAll(custNo);
	}

}
