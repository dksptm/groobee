package com.samjo.app.approval.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import com.samjo.app.upload.service.FileUploadService;

@Service
public class DocServiceImpl implements DocService {
	
	DocMapper docMapper;
	AprMapper aprMapper;
	TempMapper tempMapper;
	FileUploadService fileUploadService;
	
	@Autowired
	public DocServiceImpl(DocMapper docMapper, AprMapper aprMapper, TempMapper tempMapper,
							FileUploadService fileUploadService) {
		this.docMapper = docMapper;
		this.aprMapper = aprMapper;
		this.tempMapper = tempMapper;
		this.fileUploadService = fileUploadService;
	}
	
	// 문서전체조회.
	@Override
	public List<DocVO> docList(SearchVO searchVO) {
		return docMapper.selectDocAll(searchVO);
	}
	//		- 전체페이지 수.
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
	public int docInfoInsert(DocVO docVO) {
		// 문서테이블 등록.
		int result = docMapper.insertDoc(docVO);
		if(result != 1) {
			return -1;
		}
		// 문서등록 성공 시.
		// 연관테이블 등록.
		
		// 1.결재자 등록.
		if(docVO.getAprs() != null) {
			docVO.getAprs().forEach(apr -> {
				apr.setDocNo(docVO.getDocNo());
				apr.setCustNo(docVO.getCustNo());
				aprMapper.insertApr(apr);
			});				
		}
		
		// 2.휴가원 등록.---최종결재완료이후에!!!
		System.out.println( "템플릿 -> "+ docVO.getTempId());
		if(docVO.getTempId().equals("TP002")) {
			docVO.getPto().setDocNo(docVO.getDocNo());
			int ret = tempMapper.insertPto(docVO.getPto());
			System.out.println("insert-pto-ret->"+ret);
		}
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
		
		// 5.첨부파일 등록.
		
		
		
		// 6.문서번호 리턴.
		return docVO.getDocNo();
		 
	}
	
	// 문서등록 - 미리 번호가져오기.
	@Override
	public DocVO getDocNo() {
		return docMapper.getDocNo();
	}
		
	// 문서등록 - 파일등록.
	int ret = 0;
	@Override
	public int fileInsert(List<Map<String, Object>> flist, DocVO docVO) {
		flist.forEach(file -> {
			String fileExt = (String) file.get("fileExt");
			Long fileSize = (Long) file.get("fileSize");
			String uplName = (String) file.get("uplName");
			String saveName = (String) file.get("saveName");
			DocFileVO fileVO =
					new DocFileVO(docVO.getDocNo(), saveName, uplName, fileExt, fileSize, docVO.getDraft());
			ret += docMapper.insertDocFile(fileVO);
		});
		return ret;
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
			System.out.println("PTO => " + docVO.getPto());
			if(docVO.getTempId().equals("TP002")) {
				docVO.getPto().setDocNo(docVO.getDocNo());
				int ret = tempMapper.updatePto(docVO.getPto());
				System.out.println("update-pto-ret==>"+ret);
			} else {
				int ret = tempMapper.deletePto(docVO.getDocNo());
				System.out.println("delete-pto-ret==>"+ret);
			}
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
			// 5.문서번호 리턴.
			return docVO.getDocNo();
		} else {
			return -1;
		}
	}
	
	// 문서수정 - 파일삭제.
	@Override
	public int fileDelete(DocVO docVO) {
		List<DocFileVO> files = docMapper.selectDocFile(docVO.getDocNo());
		List<String> fileSaveNames = new ArrayList<>();
		int result = -1;
		System.out.println("files.size()-------->"+files.size());
		System.out.println("files-------->" + files);
		if(files.size() > 0) {
			for(DocFileVO file : files) {
				fileSaveNames.add(file.getSaveName());
			}
			System.out.println("fileSaveNames" + fileSaveNames);
			if(fileUploadService.deleteFileInfo(fileSaveNames, files.size())) {
				result = docMapper.deleteDocFile(docVO.getDocNo());
			} 
		} else {
			result = 0;
		}
		return result;
	}
	
	int upCnt = -1;
	@Override
	public int fileInsert(DocVO docVO, MultipartFile[] filelist) {
		if(!filelist[0].isEmpty()) {
			List<Map<String, Object>> flist = 	// 파일부터 저장하고, 정보받아옴. 
					fileUploadService.uploadFileInfo(filelist, docVO.getDraft(), docVO.getDocNo());
			flist.forEach(file -> {				// 받아온 정보로, 테이블에 등록.
				String fileExt = (String) file.get("fileExt");
				Long fileSize = (Long) file.get("fileSize");
				String uplName = (String) file.get("uplName");
				String saveName = (String) file.get("saveName");
				DocFileVO fileVO =
						new DocFileVO(docVO.getDocNo(), saveName, uplName, fileExt, fileSize, docVO.getDraft());
				upCnt += docMapper.insertDocFile(fileVO);
			});
		} else {
			System.out.println("새롭게 업로드할 파일 없음");
			upCnt = 0;
		}
		return upCnt;
	}
	

}
