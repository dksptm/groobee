package com.samjo.app.solmodule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.solmodule.mapper.SolModMapper;
import com.samjo.app.solmodule.service.ModuleService;
import com.samjo.app.solmodule.service.ModuleVO;

@Service
public class ModuleServiceImpl implements ModuleService{

	
	SolModMapper solmodMapper;
	
	@Autowired
	public ModuleServiceImpl(SolModMapper solmodMapper) {
		this.solmodMapper = solmodMapper;
	}
	
	@Override
	public List<ModuleVO> modList() {
		// TODO Auto-generated method stub
		return solmodMapper.selectModAll();
	}

}
