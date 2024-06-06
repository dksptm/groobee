package com.samjo.app.emp.mapper;

import java.util.List;

import com.samjo.app.emp.service.JobVO;

public interface JobMapper {
	
	public List<JobVO> getJobList(JobVO jobVO);
	
}
