package com.samjo.app.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
	// 프로젝트 조회
	public List<ProjectVO> PrjtAllList();
	
	// 
}
