package com.samjo.app.emp.service;

import java.util.List;

public interface JobService {
	//직급 전체조회 : 고객사가 관리하는 직급 정보 가져오기
	public List<JobVO> getJobList(JobVO jobVO);
	
	//직급 등록
	public int jobInsert(JobVO jobVO);
	
	//직급 수정
	public int jobUpdate(JobVO jobVO);
	
	//직급 삭제
	public int jobDelete(JobVO jobVO);
}	
