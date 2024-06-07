package com.samjo.app.work.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.work.service.WorkManagerSearchVO;
import com.samjo.app.work.service.WorkManagerVO;
import com.samjo.app.work.service.WorkSearchVO;
import com.samjo.app.work.service.WorkVO;

public interface WorkMapper {
	
	// 전체 페이지 수
	public int workcount(WorkSearchVO worksearchVO);
	public int managercount();
	
	// 로그인 계정 근태관리 전체출퇴근조회
	public List<WorkVO> selectAllList(WorkSearchVO worksearchVO);
	// 출근 업데이트
	public int workin(WorkVO workVO);
	//이미 체크한 값이 있는지 체크
	public int workincheck(WorkVO workVO);
	// 퇴근 업데이트
	public int workout(WorkVO workVO);
	// 조퇴 업데이트
	public int workstop(WorkVO workVO);
	
	// 계정 정보 조회
	public WorkVO selectemp(WorkVO workVO);
	
	// 관리자 페이지 근태 기록
	public List<WorkVO> selectlist(WorkSearchVO worksearchVO);
	// 관리자 페이지 전체조회
	public List<WorkManagerVO> managerWorkList(WorkManagerSearchVO workmanagersearchVO);
	// 페이지 상세 조회
	public WorkVO selectWork(WorkVO workVO);
	// 관리자 페이지 수정
	public int update(WorkVO workVO);
	
	// ip관리
	
	// ip 조회
	public List<WorkVO> selectoutip(WorkVO workVO);
	public List<WorkVO> selectinip(WorkVO workVO);
	
	// ip 추가
	public int insertinip(WorkVO workVO);
	public int insertoutip(WorkVO workVO);
	
	// ip 삭제
	public int indelete(WorkVO workVO);
	public int outdelete(WorkVO workVO);
	
	

	
}
