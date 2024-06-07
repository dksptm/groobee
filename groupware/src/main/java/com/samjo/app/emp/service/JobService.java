package com.samjo.app.emp.service;

import java.util.List;

public interface JobService {
	//직급 전체조회 : 고객사가 관리하는 직급 정보 가져오기
	public List<JobVO> getJobList(JobVO jobVO);
}
