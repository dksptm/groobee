package com.samjo.app.approval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.approval.mapper.AprMapper;
import com.samjo.app.approval.mapper.DocMapper;
import com.samjo.app.approval.mapper.TempMapper;
import com.samjo.app.approval.service.DocFileVO;
import com.samjo.app.approval.service.DocService;
import com.samjo.app.approval.service.DocVO;
import com.samjo.app.approval.service.TempVO;
import com.samjo.app.common.service.SearchVO;
import com.samjo.app.emp.service.EmpVO;
import com.samjo.app.project.service.ProjectVO;

@Service
public class DocServiceImpl implements DocService {
	
	DocMapper docMapper;
	AprMapper aprMapper;
	TempMapper tempMapper;
	
	@Autowired
	public DocServiceImpl(DocMapper docMapper, AprMapper aprMapper, TempMapper tempMapper) {
		this.docMapper = docMapper;
		this.aprMapper = aprMapper;
		this.tempMapper = tempMapper;
	}
	
	// 문서전체조회.
	@Override
	public List<DocVO> docList(SearchVO searchVO) {
		return docMapper.selectDocAll(searchVO);
	}
	
	// 문서전체조회 - 전체페이지 수.
	@Override
	public int count() {
		return docMapper.count();
	}

	// 문서조회 - 한 emp가 작성한 모든문서.
	@Override
	public List<DocVO> getMyDocList(String empId) {
		return docMapper.selectEmpDocs(empId);
	}
	
	// 문서조회 - 한 emp가 결재해야할 문서목록.
	@Override
	public List<DocVO> getMyAprList(String empId) {
		return docMapper.selectEmpApr(empId);
	}
	
	// 문서조회 - 전체문서 중 결재진행중 문서목록.
	@Override
	public List<DocVO> getIngDocList(EmpVO empVO) {
		return docMapper.selectIngDocs(empVO);
	}
	
	// 문서조회 - 전체문서 중 최종결재완료,반려 문서목록.
	@Override
	public List<DocVO> getCmpltDocList(EmpVO empVO, SearchVO searchVO) {
		return docMapper.selectCmpltDocs(empVO, searchVO);
	}
	
	// 		 - 전체페이지.
	@Override
	public int countCmplt(EmpVO empVO) {
		return docMapper.countCmplt(empVO);
	}

	// 문서단건조회.
	@Override
	public DocVO docInfo(DocVO docVO) {
		docVO = docMapper.selectDoc(docVO);
		docVO.setAprs(aprMapper.selectDocApr(docVO.getDocNo()));
		//docVO.setRefs(aprMapper.selectDocRefs(docVO.getDocNo()));
		docVO.setFiles(docMapper.selectDocFile(docVO.getDocNo()));
		//System.out.println(docVO.getRefs());
		return docVO;
	}
	
	// 단건조회 - 참조자 가져오기.
	@Override
	public List<EmpVO> docRefs(Integer docNo) {
		return aprMapper.selectDocRefs(docNo);
	}
	
	// 문서단건조회 - 업무가져오기.
	@Override
	public List<ProjectVO> docTasks(Integer docNo) {
		return docMapper.selectDocTasks(docNo);
	}
	
	// 문서작성.
	@Override
	public int docInfoInsert(DocVO docVO) {
		//System.out.println("docVO ===> " + docVO);
		// 문서테이블 등록.
		int result = docMapper.insertDoc(docVO);
		// 문서등록 완료 시.
		if(result == 1) {
			// 1.결재자 등록.
			docVO.getAprs().forEach(apr -> {
				apr.setDocNo(docVO.getDocNo());
				apr.setCustNo(docVO.getCustNo());
				aprMapper.insertApr(apr);
			});
			// 2.휴가원 등록.---최종결재완료이후에!!!
			System.out.println( "템플릿 -> "+ docVO.getTempId());
			if(docVO.getTempId().equals("TP002")) {
				docVO.getPto().setDocNo(docVO.getDocNo());
				int ret = tempMapper.insertPto(docVO.getPto());
				System.out.println("ret==>"+ret);
			}
			// 2.첨부파일 정보등록.
			/*
			 * if(docVO.getFiles() != null) { docVO.getFiles().forEach(file -> {
			 * file.setDocNo(docVO.getDocNo()); file.setUplEmp(docVO.getDeptId());
			 * docMapper.insertDocFile(file); }); }
			 */
			// 3.참조자 등록.
			if(docVO.getRefs() != null) {
				docVO.getRefs().forEach(ref -> {
					aprMapper.insertRef(docVO.getDocNo(), ref, docVO.getCustNo());
				});				
			}
			// 4.업무연결 등록.
			if(docVO.getTasks() != null) {
				docVO.getTasks().forEach(task -> {
					docMapper.insertTaskDoc(docVO.getDocNo(), task, docVO.getCustNo());
				});				
			}
			System.out.println("docVO ===> " + docVO);
			// 5.문서번호 리턴.
			return docVO.getDocNo();
		} else {  // 문서등록 실패 시.		
			return -1; 
		}
	}
	
	// 문서작성 - 미리 번호가져오기.
	@Override
	public DocVO getDocNo() {
		return docMapper.getDocNo();
	}
		
	// 문서등록 - 파일등록.
	@Override
	public int fileInsert(DocFileVO fileVO) {
		return docMapper.insertDocFile(fileVO);
	}
	
	// 문서작성 - 템플릿 전체조회.
	@Override
	public List<TempVO> getCustTemps() {
		return tempMapper.selectCustTemps();
	}

	// 문서수정.
	@Override
	public int docInfoUpdate(DocVO docVO) {
		// 문서테이블 수정.
		int result = docMapper.updateDoc(docVO);
		System.out.println("결재자들--" + docVO.getAprs());
		System.out.println("마지막라인--"+docVO.getFinalLine());
		// 문서수정 완료 시.
		if(result == 1) {
			// 1.결재자 삭제 후 재등록
			aprMapper.deleteApr(docVO.getDocNo());
			docVO.getAprs().forEach(apr -> {
				apr.setDocNo(docVO.getDocNo());
				apr.setCustNo(docVO.getCustNo());
				aprMapper.insertApr(apr);
			});
			// 2.휴가원 수정 또는 삭제.
			if(docVO.getTempId().equals("TP002")) {
				int ret = tempMapper.updatePto(docVO.getPto());
				System.out.println("update-ret==>"+ret);
			} else {
				int ret = tempMapper.deletePto(docVO.getDocNo());
				System.out.println("delete-ret==>"+ret);
			}
			// 2.첨부파일 정보등록.
			/*
			 * if(docVO.getFiles() != null) { docVO.getFiles().forEach(file -> {
			 * file.setDocNo(docVO.getDocNo()); file.setUplEmp(docVO.getDeptId());
			 * docMapper.insertDocFile(file); }); }
			 */
			// 3.참조자 기존참조자 삭제 후 재등록.
			aprMapper.deleteRef(docVO.getDocNo());
			if(docVO.getRefs() != null) {
				docVO.getRefs().forEach(ref -> {
					aprMapper.insertRef(docVO.getDocNo(), ref, docVO.getCustNo());
				});				
			}
			// 4.기존 연결업무 삭제 후 재등록.
			docMapper.deleteTaskDoc(docVO.getDocNo());
			if(docVO.getTasks() != null) {
				docVO.getTasks().forEach(task -> {
					docMapper.insertTaskDoc(docVO.getDocNo(), task, docVO.getCustNo());
				});				
			}
			//System.out.println("docVO ===> " + docVO);
			// 5.문서번호 리턴.
			return docVO.getDocNo();
		} else {
			return -1;
		}
	}

}
