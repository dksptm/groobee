package com.samjo.app.approval.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.approval.mapper.AprMapper;
import com.samjo.app.approval.mapper.DocMapper;
import com.samjo.app.approval.service.DocService;
import com.samjo.app.approval.service.DocVO;
import com.samjo.app.common.service.SearchVO;

@Service
public class DocServiceImpl implements DocService {
	
	DocMapper docMapper;
	AprMapper aprMapper;
	
	@Autowired
	public DocServiceImpl(DocMapper docMapper, AprMapper aprMapper) {
		this.docMapper = docMapper;
		this.aprMapper = aprMapper;
	}
	
	@Override
	public int docInfoInsert(DocVO docVO) {
		int result = docMapper.insertDoc(docVO);
		if(result == 1) {
			docVO.getAprs().forEach(apr -> {
				apr.setDocNo(docVO.getDocNo());
				aprMapper.insertApr(apr);
			});
			return docVO.getDocNo();
		} else {			
			return -1;
		}
	}

	@Override
	public DocVO docInfo(DocVO docVO) {
		return docMapper.selectDoc(docVO);
	}

	@Override
	public int docInfoUpdate(DocVO docVO) {
		return docMapper.updateDoc(docVO);
	}

	@Override
	public List<DocVO> docList(SearchVO searchVO) {
		return docMapper.selectDocAll(searchVO);
	}

	@Override
	public int count() {
		return docMapper.count();
	}

	@Override
	public DocVO getDocNo() {
		return docMapper.getDocNo();
	}

}
