package com.samjo.app.solmodule.mapper;

import java.util.List;

import com.samjo.app.solmodule.service.ModuleVO;

public interface SolModMapper {
	
	//모듈 전체조회
	public List<ModuleVO> selectModAll();

}
