package com.samjo.app.common.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.samjo.app.project.service.ProjectVO;

import lombok.Data;

@Data
public class SearchVO {
	private int page;
	private String searchCondition;
	private String keyword;
	
	// 전자결재 관련.
	private String keywordCondition;
	private String aprStatCondition;
	private String docStatCondition;
	private String draftStatCondition;
	private List<ProjectVO> schTasks;
	
	private String sortCondition;
	private String dateCondition;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dtStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dtEnd;
	
	private String path;
	
	// 계약 관련.
	private String ctName;
	private String ctStat;
	private String ctSort;
	private String ctNo;
	private String custNo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ctStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ctEnd;
	private String ctDt;
	
	
	// 이메일 관련 inboxList, emailList, wastedList 전부 공유
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sentDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sdStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sdEnd;	
	private String emTitle;
	private String sender;
	private String recp;
	private String refer;
	private String recpType;
	private String emSort;
	private boolean hasFile; //첨부파일 유무 -> 있으면 클립이미지
	
	
	// 업무 관련
	private int taskNo;
	private String taskName;
	private String taskSort;
	private String taskType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date taskStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date taskDue;
	private String taskDt;
	private String prjtName;
	private String prjtMat;
	private int participantsCnt;
	
	private String prjtId;
	private String prjtSort;
	private String prjtStat;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prjtStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date prjtDue;
	private String prjtDt;

}
