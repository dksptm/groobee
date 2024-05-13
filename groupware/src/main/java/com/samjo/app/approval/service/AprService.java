package com.samjo.app.approval.service;

import org.springframework.stereotype.Service;

@Service
public interface AprService {
	// 상신하기(결재자가 기안자).
	public int goToApr(AprVO aprVO);
}
