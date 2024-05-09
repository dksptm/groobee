package com.samjo.app.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.samjo.app.project.mapper.ProjectMapper;
import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;

public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectMapper projectMapper;
	
	@Override
	public List<ProjectVO> PrjtAllList() {
		return projectMapper.selectPrjtAllList();
	}

}
