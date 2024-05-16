package com.samjo.app.approval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.approval.mapper.AprMapper;
import com.samjo.app.approval.mapper.DocMapper;
import com.samjo.app.approval.mapper.TempMapper;
import com.samjo.app.approval.service.DocService;
import com.samjo.app.approval.service.DocVO;
import com.samjo.app.approval.service.TempVO;
import com.samjo.app.common.service.SearchVO;

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
	
	// 문서작성.
	@Override
	public int docInfoInsert(DocVO docVO) {
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
			// 2.첨부파일 정보등록.
			if(docVO.getFiles() != null) {
				docVO.getFiles().forEach(file -> {
					file.setDocNo(docVO.getDocNo());
					file.setUplEmp(docVO.getDeptId());
					docMapper.insertDocFile(file);
				});				
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
			
			System.out.println("docVO ===> " + docVO);
			// 5.문서번호 리턴.
			return docVO.getDocNo();
			
		} else {  // 문서등록 실패 시.		
			return -1; 
		}
	}

	// 문서단건조회.
	@Override
	public DocVO docInfo(DocVO docVO) {
		docVO = docMapper.selectDoc(docVO);
		docVO.setAprs(aprMapper.selectDocApr(docVO.getDocNo()));
		return docVO;
	}

	// 문서수정.
	@Override
	public int docInfoUpdate(DocVO docVO) {
		return docMapper.updateDoc(docVO);
	}

	// 문서전체조회.
	@Override
	public List<DocVO> docList(SearchVO searchVO) {
		return docMapper.selectDocAll(searchVO);
	}
	
	// 문서전체조회 - 전체페이지.
	@Override
	public int count() {
		return docMapper.count();
	}

	// 문서작성 - 미리 번호가져오기.
	@Override
	public DocVO getDocNo() {
		return docMapper.getDocNo();
	}

	// 문서작성 - 템플릿 전체조회.
	@Override
	public List<TempVO> getCustTemps() {
		return tempMapper.selectCustTemps();
	}

}
