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
	public int workin(WorkVO workVO) {
		int result = 0;
		int workInCheck = workMapper.workincheck(workVO);
		
		if(workInCheck == 1) {
			result = -1;
		}else {
		  workMapper.workin(workVO);
		  result = 0;	
		}
		return result;
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
	// 수정 처리
	@Override
	public Map<String, Object> update(WorkVO workVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
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

	// 사원 정보(근속일수, 휴가사용 등) 
	@Override
	public WorkVO selectemp(WorkVO workVO) {
		return workMapper.selectemp(workVO);
	}

	
	// ip 관련
	
	// outip 조회
	@Override
	public List<WorkVO> selectoutip(WorkVO workVO) {
		return workMapper.selectoutip(workVO);
	}

	// inip조회
	@Override
	public List<WorkVO> selectinip(WorkVO workVO) {
		return workMapper.selectinip(workVO);
	}

	// inip 등록
	@Override
	public int insertinip(WorkVO workVO) {
		return workMapper.insertinip(workVO);
	}

	// outip 등록
	@Override
	public int insertoutip(WorkVO workVO) {
		return workMapper.insertoutip(workVO);
	}

	// inip 삭제
	@Override
	public int indelete(WorkVO workVO) {
		return workMapper.indelete(workVO);
	}

	// outip 삭제
	@Override
	public int outdelete(WorkVO workVO) {
		return workMapper.outdelete(workVO);
	}


	@Override
	public List<WorkVO> inipcheck(WorkVO workVO) {
		return workMapper.inipcheck(workVO);
	}


	
	

	
	
}
