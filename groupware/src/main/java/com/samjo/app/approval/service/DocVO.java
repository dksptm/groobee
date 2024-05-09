package com.samjo.app.approval.service;

import java.util.Date;

import lombok.Data;

@Data
public class DocVO {
	private Integer docNo;
	private String draft;
	private String deptId;
	private String title;
	private String cntn;
	private Date draftDt;
	private String draftStat;
	private String tempId;
	private Integer finalLine;
	private Integer nowLine;
	private String docStat;
    private Date cmpltDt;
}
