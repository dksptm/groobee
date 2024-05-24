package com.samjo.app.approval.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	// (전체페이지 수)
	@Override
	public int count() {
		return docMapper.count();
	}

	// 문서조회 - 한 emp가 작성한 모든문서.
	@Override
	public List<DocVO> getMyDocList(String empId, SearchVO searchVO) {
		List<DocVO> list = docMapper.selectEmpDocs(empId, searchVO);
		for(DocVO doc : list) {
			System.out.println(doc.getTaskList());
		}
		return list;
	}
	@Override
	public int countEmpDocs(String empId) {
		return docMapper.countEmpDocs(empId);
	}
	
	// 문서조회 - 한 emp가 결재해야할 문서목록.
	@Override
	public List<DocVO> getMyAprList(String empId, SearchVO searchVO) {
		return docMapper.selectEmpApr(empId, searchVO);
	}
	@Override
	public int countEmpApr(String empId) {
		return docMapper.countEmpApr(empId);
	}
	
	// 문서조회 - 전체문서 중 결재진행중 문서목록.
	@Override
	public List<DocVO> getIngDocList(EmpVO empVO, SearchVO searchVO) {
		return docMapper.selectIngDocs(empVO, searchVO);
	}
	@Override
	public int countIng(EmpVO empVO) {
		return docMapper.countIng(empVO);
	}
	
	// 문서조회 - 전체문서 중 최종결재완료/반려 문서목록.
	@Override
	public List<DocVO> getCmpltDocList(EmpVO empVO, SearchVO searchVO) {
		return docMapper.selectCmpltDocs(empVO, searchVO);
	}
	@Override
	public int countCmplt(EmpVO empVO, SearchVO searchVO) {
		return docMapper.countCmplt(empVO, searchVO);
	}

	// 문서단건조회.
	@Override
	public DocVO docInfo(DocVO docVO) {
		docVO = docMapper.selectDoc(docVO);
		docVO.setAprs(aprMapper.selectDocApr(docVO.getDocNo()));
		//docVO.setRefs(aprMapper.selectDocRefs(docVO.getDocNo()));
		docVO.setFiles(docMapper.selectDocFile(docVO.getDocNo()));
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
	
	// 문서등록 화면 - 템플릿 전체조회.
	@Override
	public List<TempVO> getCustTemps() {
		return tempMapper.selectCustTemps();
	}

	// 문서등록.
	@Override
	@Transactional
	public int docInfoInsert(DocVO docVO, List<Map<String, Object>> fileInfoList) {
		
		int result = 0;
		
		// 0.문서 등록.
		result = docMapper.insertDoc(docVO);
		
		if(result != 1) {
			return -1;
		}
		
		result = 0;
		
		// 1.결재자 등록.
		if(docVO.getAprs() != null) {
			aprMapper.insertApr(docVO);
		}
		
		// 2.휴가원 등록.--최종결재완료이후 처리필요.
		if(docVO.getTempId().equals("TP002")) {
			docVO.getPto().setDocNo(docVO.getDocNo());
			tempMapper.insertPto(docVO.getPto());
		}
		
		// 3.참조자 등록.
		if(docVO.getRefs() != null) {
			aprMapper.insertRef(docVO);
		}
		
		// 4.업무연결 등록.
		if(docVO.getTasks() != null) {
			docMapper.insertTaskDoc(docVO);				
		}
		
		// 5.첨부파일 등록.
		if(fileInfoList.size() > 0) {
			List<DocFileVO> docFileList = getDocFileList(docVO, fileInfoList);
			docMapper.insertDocFile(docFileList);			
		}
		
		// 6. 문서번호 리턴.
		result = docVO.getDocNo();
		
		// COMMIT : 문서번호. / ROLLBACK : 0.
		return result;
		
	}
	
	// 문서수정.
	@Override
	@Transactional
	public int docInfoUpdate(DocVO docVO, List<Map<String, Object>> fileInfoList, String flag) {
		
		int result = 0;
		
		// 0.문서테이블 수정.
		result = docMapper.updateDoc(docVO);
		
		if(result != 1) {
			return -1;
		} 
		
		result = 0;
		
		// 1.기존 결재자 삭제 후 재등록
		aprMapper.deleteApr(docVO.getDocNo());
		if(docVO.getAprs() != null) {
			aprMapper.insertApr(docVO);
		}
		
		// 2.휴가원 수정
		if(docVO.getTempId().equals("TP002")) {
			docVO.getPto().setDocNo(docVO.getDocNo());
			tempMapper.updatePto(docVO.getPto());
		} else {
			tempMapper.deletePto(docVO.getDocNo());
		}
		
		// 3.기존 참조자 삭제 후 재등록
		aprMapper.deleteRef(docVO.getDocNo());
		if(docVO.getRefs() != null) {
			aprMapper.insertRef(docVO);
		}
		
		// 4.기존 연결업무 삭제 후 재등록
		docMapper.deleteTaskDoc(docVO.getDocNo());
		if(docVO.getTasks() != null) {
			docMapper.insertTaskDoc(docVO);				
		}
		
		// 5.기존 파일 삭제(flag == YES) 후 재등록
		if(flag.equals("YES")) {
			docMapper.deleteDocFile(docVO.getDocNo());					
		}
		if(fileInfoList.size() > 0) {
			List<DocFileVO> docFileList = getDocFileList(docVO, fileInfoList);
			docMapper.insertDocFile(docFileList);			
		}
		
		// 6. 문서번호 리턴.
		result = docVO.getDocNo();
		
		// COMMIT : 문서번호. / ROLLBACK : 0.
		return result;
	}
	
	// DocVO docVO => List<String> fileSaveNames
	@Override
	public List<String> getDocFileSavaNames(DocVO docVO) {
		
		List<DocFileVO> files = docMapper.selectDocFile(docVO.getDocNo());
		List<String> fileSaveNames = new ArrayList<>();
		
		for(DocFileVO file : files) {
			fileSaveNames.add(file.getSaveName());
		}
		
		return fileSaveNames;
		
	}
	
	// List<Map<String, Object>> => DocFileVO.
	public List<DocFileVO> getDocFileList(DocVO docVO, List<Map<String, Object>> fileInfoList) {
		
		List<DocFileVO> docFileList = new ArrayList<>();
		
		fileInfoList.forEach(file -> { // object 형변환해서 fileVO 생성.
			DocFileVO fileVO =
					new DocFileVO(docVO.getDocNo(), (String) file.get("saveName"), 
									(String) file.get("uplName"), (String) file.get("fileExt"), 
									(Long) file.get("fileSize"), docVO.getDraft());
			docFileList.add(fileVO);
		});
		
		return docFileList;
	}


}
