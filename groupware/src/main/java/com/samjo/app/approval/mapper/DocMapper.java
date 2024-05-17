package com.samjo.app.approval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samjo.app.approval.service.DocFileVO;
import com.samjo.app.approval.service.DocVO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.project.service.ProjectVO;

public interface DocMapper {
	// 문서등록
	public int insertDoc(DocVO docVO);
	// 문서등록 - 문서번호 조회.
	public DocVO getDocNo();
	// 단건조회
	public DocVO selectDoc(DocVO docVO);
	// 문서수정
	public int updateDoc(DocVO docVO);
	// 문서전체
	public List<DocVO> selectDocAll(SearchVO searchVO);
	// 문서전체-페이징
	public int count();
	
	// 첨부파일 등록
	public int insertDocFile(DocFileVO docFile);
	// 한 문서의 첨부파일 조회
	public List<DocFileVO> selectDocFile(@Param("dno")Integer docNo);
	
	// 업무와 연결(문서-업무 테이블)
	public int insertTaskDoc(@Param("dno")Integer docNo, 
			@Param("tno")Integer taskNo, @Param("cno")String custNo);
	// 한 문서의 업무 조회
	public List<ProjectVO> selectDocTasks(@Param("dno")Integer docNo);
	
	// 한 직원이 작성한 문서.
	public List<DocVO> selectEmpDocs(@Param("eid")String empId);
	
}	
