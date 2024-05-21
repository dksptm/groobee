package com.samjo.app.approval.service;

import lombok.Data;

@Data
public class TempVO {
	
	// 실제경로, 호출경로 추가예정.
	private String tempNo;
    private String tempName;
    private String tempInfo;
    private String saveName;
    private String uplName;
    private String custNo;
    private String tempImg;
    
    // 테스트용
    private String content;
    
    // pto
    private Integer docNo;
	private String ptoType;
	private String ptoStartDt;
	private String ptoEndDt;
	private String reason;
}
