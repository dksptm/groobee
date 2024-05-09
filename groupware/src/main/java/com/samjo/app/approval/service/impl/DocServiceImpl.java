package com.samjo.app.approval.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samjo.app.approval.mapper.DocMapper;
import com.samjo.app.approval.service.DocService;
import com.samjo.app.approval.service.DocVO;

@Service
public class DocServiceImpl implements DocService {
	
	DocMapper docMapper;
	
	@Autowired
	public DocServiceImpl(DocMapper docMapper) {
		this.docMapper = docMapper;
	}

	@Override
	public int docInfoInsert(DocVO docVO) {
		return docMapper.insertDoc(docVO);
	}

}
