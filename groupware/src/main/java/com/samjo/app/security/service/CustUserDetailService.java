package com.samjo.app.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samjo.app.security.mapper.LoginMapper;

@Service
public class CustUserDetailService implements UserDetailsService{

	@Autowired
	LoginMapper loginMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LoginVO loginVO = loginMapper.getUserInfo(username);
		
		if(loginVO == null) {
			throw new UsernameNotFoundException("No User");
		}
		
		return new LoginUserVO(loginVO);
	}

}
