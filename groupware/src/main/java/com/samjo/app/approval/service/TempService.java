package com.samjo.app.approval.service;

import java.util.Map;

public interface TempService {
	
	// 휴가원 등록
	public Map<String, Object> insertPto(TempVO temp);
	// 휴가원 조회
	public TempVO selectPto(Integer docNo);
}
