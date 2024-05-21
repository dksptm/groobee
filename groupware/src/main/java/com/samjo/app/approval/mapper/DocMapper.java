package com.samjo.app.approval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.approval.service.DocFileVO;
import com.samjo.app.approval.service.DocVO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;

public interface DocMapper {

	// 문서전체
	public List<DocVO> selectDocAll(SearchVO searchVO);
	// 문서전체-페이징
	public int count();
	// 한 직원이 작성한 문서.
	public List<DocVO> selectEmpDocs(@Param("eid")String empId, @Param("sch")SearchVO searchVO);
	//		-전체count.
	public int countEmpDocs(@Param("eid")String empId);	
	// 내가 현재 결재해야할 문서리스트.
	public List<DocVO> selectEmpApr(@Param("eid")String empId, @Param("sch")SearchVO searchVO);
	//		-전체count.
	public int countEmpApr(@Param("eid")String empId);	
	// 결재진행중인 문서리스트.(--추후 권한별 리스트출력)
	public List<DocVO> selectIngDocs(@Param("emp")EmpVO empVO, @Param("sch")SearchVO searchVO);
	//		-전체count.
	public int countIng(EmpVO empVO);
	// 완료된 문서리스트(최종결재완료 또는 반려문서)
	public List<DocVO> selectCmpltDocs(@Param("emp")EmpVO empVO, @Param("sch")SearchVO searchVO);
	// 		-전체count.
	public int countCmplt(EmpVO empVO);
	
	
	// 단건조회
	public DocVO selectDoc(DocVO docVO);
	// 한 문서의 업무 조회
	public List<ProjectVO> selectDocTasks(@Param("dno")Integer docNo);
	// 한 문서의 첨부파일 조회
	public List<DocFileVO> selectDocFile(@Param("dno")Integer docNo);
	
	// 문서등록
	public int insertDoc(DocVO docVO);
	// 문서등록 - 문서번호 조회.
	public DocVO getDocNo();
	// 첨부파일 등록
	public int insertDocFile(DocFileVO docFile);
	// 업무와 연결(문서-업무 테이블)
	public int insertTaskDoc(@Param("dno")Integer docNo, 
			@Param("tno")Integer taskNo, @Param("cno")String custNo);
	
	// 문서수정
	public int updateDoc(DocVO docVO);
	
	// 업무-문서 삭제.
	public int deleteTaskDoc(@Param("dno")Integer docNo);
	
}	
