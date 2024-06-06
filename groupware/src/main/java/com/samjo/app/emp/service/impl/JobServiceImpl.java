package com.samjo.app.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.emp.mapper.JobMapper;
import com.samjo.app.emp.service.JobService;
import com.samjo.app.emp.service.JobVO;


@Service
public class JobServiceImpl implements JobService {
	
	@Autowired
	JobMapper jobMapper;
	
	
	//직급 전체조회 : 고객사가 관리하는 직급 정보 가져오기
	@Override
	public List<JobVO> getJobList(JobVO jobVO) {
		
		return jobMapper.getJobList(jobVO);
	}

}
