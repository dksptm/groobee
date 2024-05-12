package com.samjo.app.approval.mapper;

import java.util.List;

import com.samjo.app.approval.service.DocVO;
import com.samjo.app.common.service.SearchVO;

public interface DocMapper {
	// 문서등록
	public int insertDoc(DocVO docVO);
	// 단건조회
	public DocVO selectDoc(DocVO docVO);
	// 문서수정
	public int updateDoc(DocVO docVO);
	// 문서전체
	public List<DocVO> selectDocAll(SearchVO searchVO);
	// 문서전체-페이징
	public int count();
}	
