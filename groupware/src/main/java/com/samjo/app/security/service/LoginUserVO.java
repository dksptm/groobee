package com.samjo.app.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserVO implements UserDetails {

	private LoginVO loginVO;

	public LoginUserVO(LoginVO loginVO) {
		this.loginVO = loginVO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> auth = new ArrayList<>();

		for (String roleMod : loginVO.getRoleMod()) {
			auth.add(new SimpleGrantedAuthority(roleMod));
		}
		auth.add(new SimpleGrantedAuthority(getEmpId()));
		auth.add(new SimpleGrantedAuthority(getCustNo()));
		auth.add(new SimpleGrantedAuthority(getCtNo()));
		auth.add(new SimpleGrantedAuthority(getDeptId()));
		auth.add(new SimpleGrantedAuthority(getJobNo()));
		auth.add(new SimpleGrantedAuthority(getPermId()));
		auth.add(new SimpleGrantedAuthority(getEmpStat()));
		auth.add(new SimpleGrantedAuthority(getAbleCheck()));
		return auth;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return loginVO.getPw();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return loginVO.getEmpName();
		//return loginVO.getEmpId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getEmpId() {
		return loginVO.getEmpId();
	}
	
	public String getCustNo() {
		return loginVO.getCustNo();
	}

	public String getCtNo() {
		return Integer.toString(loginVO.getCtNo());
	}

	public String getDeptId() {
		return loginVO.getDeptId();
	}

	public String getJobNo() {
		return Integer.toString(loginVO.getJobNo());
	}

	public String getPermId() {
		return loginVO.getPermId();
	}

	public String getEmpStat() {
		return loginVO.getEmpStat();
	}
	
	public String getAbleCheck() {
		return loginVO.getAbleCheck();
	}
}
