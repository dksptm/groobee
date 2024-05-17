package com.samjo.app.security.mapper;

import com.samjo.app.security.service.LoginVO;

public interface LoginMapper {
	public LoginVO getUserInfo(String username);
}
