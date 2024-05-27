package com.samjo.app.project.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.mapper.DeptMapper;
import com.samjo.app.project.mapper.ProjectMapper;
import com.samjo.app.project.service.ProjectService;
import com.samjo.app.project.service.ProjectVO;


@Service
public class ProjectServiceImpl implements ProjectService {
	
	ProjectMapper projectMapper;
	
	@Autowired
	public ProjectServiceImpl(ProjectMapper projectMapper) {
		this.projectMapper = projectMapper;
	}
	
	@Override // 프로젝트 전체조회
	public List<ProjectVO> PrjtAllList() {
		return projectMapper.selectPrjtAllList();
	}
	
	@Override // 프로젝트 단건조회
	public ProjectVO prjtInfo(ProjectVO projectVO) {
		return projectMapper.selectPrjt(projectVO);
	}
	
	@Override // 프로젝트 등록
	public int prjtInsert(ProjectVO projectVO) {
		System.out.println("프로젝트등록:"+ projectVO);
		return projectMapper.insertPrjt(projectVO);
	}
	
	@Override // 프로젝트 수정
	public Map<String, Object> prjtUpdate(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = projectMapper.updatePrjt(projectVO);
		if (result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", projectVO);
		return map;
	}

	@Override // 프로젝트 삭제
	public Map<String, Object> prjtDelete(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		int prjtId = Integer.parseInt(projectVO.getPrjtId());
		int result = projectMapper.deletePrjt(prjtId);

		if (result == 1) {
			map.put("prjtId", projectVO.getPrjtId());
		}
		return map;
	}
	
	@Override // 프로젝트(하위)업무 전체조회
	public List<ProjectVO> taskAllList(SearchVO searchVO) {
		System.out.println("serchVO--->:" + searchVO);
		return projectMapper.selectTaskAllList(searchVO);
	}
	
	@Override // 프로젝트업무 페이징
	public int count(SearchVO searchVO) {
		return projectMapper.taskCount(searchVO);
	}
	
	@Override // 프로젝트(하위)업무 단건조회
	public ProjectVO taskInfo(ProjectVO projectVO) {
		return projectMapper.selectTask(projectVO);
	}
	
	@Override // 프로젝트(하위) 등록
	public int taskInsert(ProjectVO projectVO) {
		return projectMapper.insertTask(projectVO);
	}
	
	@Override //프로젝트(하위)업무 수정
	public Map<String, Object> taskUpdate(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = projectMapper.updateTask(projectVO);
		if (result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", projectVO);
		return map;
	}

	@Override // 프로젝트(하위) 업무 삭제
	public Map<String, Object> taskDelete(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		int result = projectMapper.deleteTask(projectVO.getTaskNo());
		//System.out.println("result:"+ result);
		if (result == 1) {
			map.put("taskNo", projectVO.getTaskNo());
		}
		return map;
	}
	
	@Override // 상시(주기적)업무 등록
	public int reguInsert(ProjectVO projectVO) {
		return projectMapper.reguInsert(projectVO);
	}
	@Override // 상시(주기적)업무 조회
	public List<ProjectVO> reguAllList() {
		return projectMapper.selectReguAllList();
	}
	@Override // 상시 단건
	public ProjectVO reguInfo(ProjectVO projectVO) {
		return projectMapper.selectRegu(projectVO);
	}

	@Override // 상시 수정
	public Map<String, Object> reguUpdate(ProjectVO projectVO) {
			Map<String, Object> map = new HashMap<>();
			boolean isSuccessed = false;
			int result = projectMapper.updateRegu(projectVO);
			if (result == 1) {
				isSuccessed = true;
			}
			map.put("result", isSuccessed);
			map.put("target", projectVO);
			return map;
		}

	@Override // 상시 삭제 
	public Map<String, Object> reguDelete(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		int reguId = Integer.parseInt(projectVO.getReguId());
		int result = projectMapper.deleteRegu(reguId);

		if (result == 1) {
			map.put("reguId", projectVO.getReguId());
		}
		return map;
	}
	
	
	@Override // 협력업체 조회
	public List<ProjectVO> CoopCoAllList() {
		return projectMapper.selectCoopCoAllList();
	}
	@Override // 협력업체 등록
	public int coopInsert(ProjectVO projectVO) {
		//System.out.println("협력업체:"+ projectVO);
		return projectMapper.insertCoop(projectVO);
	}
	@Override // 협력업체 단건
	public ProjectVO coopInfo(ProjectVO projectVO) {
		return projectMapper.selectCoop(projectVO);
	}
	@Override // 협력업체 수정
	public Map<String, Object> coopUpdate(ProjectVO projectVO) {
		// js -> let map = {}; 
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = projectMapper.updateCoop(projectVO);
		if (result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		// js -> map.target = { coopCoNo : '', coName : '', ...};
		map.put("target", projectVO);
		return map;
	}
	@Override // 협력업체 삭제
	/*public int coopDelete(ProjectVO projectVO) {
		return projectMapper.deleteCoop(projectVO);
	}*/
	public Map<String, Object> coopDelete(ProjectVO projectVO) {
		Map<String, Object> map = new HashMap<>();
		int result = projectMapper.deleteCoop(projectVO.getCoopCoNo());
		//System.out.println("result:"+ result);
		if (result == 1) {
			map.put("coopCoNo", projectVO.getCoopCoNo());
		}
		return map;
	}

	
	
	// 효주 - 업무간단조회
	@Override
	public List<ProjectVO> myCustTasks(String custNo) {
		return projectMapper.selectTasks(custNo);
	}
	// 효주 끝.



}
