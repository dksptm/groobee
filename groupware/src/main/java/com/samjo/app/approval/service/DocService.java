package com.samjo.app.approval.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;

public interface DocService {
	
	// 문서전체조회
	public List<DocVO> docList(SearchVO searchVO);
	// 전체페이지
	public int count();
	// 한 직원이 작성한 모든문서.
	public List<DocVO> getMyDocList(String empId, SearchVO searchVO);
	public int countEmpDocs(String empId);
	// 내가 현재 결재해야할 문서리스트.
	public List<DocVO> getMyAprList(String empId, SearchVO searchVO);
	public int countEmpApr(String empId);
	// 전체문서 중 결재진행중인 문서리스트
	public List<DocVO> getIngDocList(EmpVO empVO, SearchVO searchVO);
	public int countIng(EmpVO empVO);
	// 전체문서 중 완료 문서리스트
	public List<DocVO> getCmpltDocList(EmpVO empVO, SearchVO searchVO);
	public int countCmplt(EmpVO empVO, SearchVO searchVO);
	
	// 단건조회
	public DocVO docInfo(DocVO docVO);
	// 단건조회 - 참조자.
	public List<EmpVO> docRefs(Integer docNo);
	// 단건조회 - 업무.
	public List<ProjectVO> docTasks(Integer docNo);
	
	// 문서작성/수정시 - 템플릿 전체조회.
	public List<TempVO> getCustTemps();
	// 문서작성
	public int docInfoInsert(DocVO docVO);
	// 문서작성 - 문서번호 조회.
	public DocVO getDocNo();
	// 파일등록
	public int fileInsert(List<Map<String, Object>> flist, DocVO docVO);	
	
	// 문서수정
	public int docInfoUpdate(DocVO docVO);
	
	// 파일삭제
	public int fileDelete(DocVO docVO);
	// 파일등록
	public int fileInsert(DocVO docVO, MultipartFile[] filelist);
	
}
