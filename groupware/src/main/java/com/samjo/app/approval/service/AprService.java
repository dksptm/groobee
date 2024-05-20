package com.samjo.app.approval.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface AprService {
	// 상신하기(결재자가 기안자).
	public Map<String, Object> goToApr(AprVO aprVO);
	// 결재하기.
	public Map<String, Object> aprOk(AprVO aprVO);
	// 반려하기.
	public Map<String, Object> aprNg(AprVO aprVO);
	
}
