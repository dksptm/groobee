package com.samjo.app.work.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.work.mapper.WorkMapper;
import com.samjo.app.work.service.WorkManagerSearchVO;
import com.samjo.app.work.service.WorkManagerVO;
import com.samjo.app.work.service.WorkSearchVO;
import com.samjo.app.work.service.WorkService;
import com.samjo.app.work.service.WorkVO;

@Service
public class WorkServiceImpl implements WorkService{
	
	@Autowired
	WorkMapper workMapper;
	
	// 로그인 전체 데이터 수
	@Override
	public List<WorkVO> selectlist(WorkSearchVO worksearchVO) {
		return workMapper.selectlist(worksearchVO);
	}
	
	
	// 전체 데이터 수
	@Override
	public int workcount(WorkSearchVO worksearchVO) {
		return workMapper.workcount(worksearchVO);
	}
	
	// 출근 수정
	@Override
	public Map<String, Object> workin(WorkVO workVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = workMapper.workin(workVO);
		
		if(result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		map.put("target", workVO);
		
		return map;
	}
	// 퇴근 수정	
	@Override
	public Map<String, Object> workout(WorkVO workVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = workMapper.workout(workVO);
		
		if(result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		map.put("target", workVO);
		
		return map;
	}
	
	// 관리자 페이지 전체조회
	@Override
	public List<WorkManagerVO> managerWorkList(WorkManagerSearchVO workmanagersearchVO) {
		return workMapper.managerWorkList(workmanagersearchVO);
	}
	// 페이지 상세조회
	@Override
	public WorkVO selectWork(WorkVO workVO) {
		return workMapper.selectWork(workVO);
	}

	@Override
	public int managercount() {
		return workMapper.managercount();
	}
	
	// 관리자요청 전체 리스트
		@Override
		public List<WorkVO> workList(WorkSearchVO worksearchVO) {
			return workMapper.selectAllList(worksearchVO);
		}

	@Override
	public Map<String, Object> update(WorkVO workVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		System.err.println(workVO.getEmpId());
		int result = workMapper.update(workVO);
		
		if(result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		map.put("target", workVO);
		
		return map;
	}


	@Override
	public Map<String, Object> workstop(WorkVO workVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = workMapper.workstop(workVO);
		
		if(result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		map.put("target", workVO);
		
		return map;
	}


	@Override
	public WorkVO selectemp(WorkVO workVO) {
		return workMapper.selectemp(workVO);
	}
	
	

	
	
}
