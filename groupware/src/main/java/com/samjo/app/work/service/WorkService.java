package com.samjo.app.work.service;

import java.util.List;

public interface WorkService {

	// 근태 전체 조회
	public List<WorkVO> workList();
	
	public WorkVO getWorkId();
	// 근태등록(임시)
	public int insertwork(WorkVO workVO);
	
}
