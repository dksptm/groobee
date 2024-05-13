package com.samjo.app.approval.mapper;

import com.samjo.app.approval.service.AprVO;

public interface AprMapper {
	// 결재자등록(DocService에서 사용).
	public int insertApr(AprVO aprVO);
	// 상신하기.
	public int updateMyApr(AprVO aprVO);

}
