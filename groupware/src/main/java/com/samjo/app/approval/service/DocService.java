package com.samjo.app.approval.service;

import java.util.List;

import com.samjo.app.common.service.SearchVO;

public interface DocService {
	// 문서작성
	public int docInfoInsert(DocVO docVO);
	// 문서작성 - 문서번호 조회.
	public DocVO getDocNo();
	// 문서작성 - 템플릿 전체조회.
	public List<TempVO> getCustTemps();
	// 단건조회
	public DocVO docInfo(DocVO docVO);
	// 문서수정
	public int docInfoUpdate(DocVO docVO);
	// 문서전체조회
	public List<DocVO> docList(SearchVO searchVO);
	// 전체페이지
	public int count();
}
