package com.samjo.app.emp.mapper;

import java.util.List;

import com.samjo.app.emp.service.JobVO;

public interface JobMapper {
	
	public List<JobVO> getJobList(JobVO jobVO);
	
	public int jobInsert(JobVO jobVO);
	
	public int jobUpdate(JobVO jobVO);
	
	public int jobDelete(JobVO jobVO);
}
