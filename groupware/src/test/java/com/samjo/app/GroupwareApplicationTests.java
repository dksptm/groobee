package com.samjo.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class GroupwareApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	public void testEncoder() {
		String password = "1234";

		String enPwd = passwordEncoder.encode(password);
		System.out.println("enPwd : " + enPwd);
//														  원래 데이터, 암호화된 데이터
		boolean matchResult = passwordEncoder.matches(password, enPwd);
		System.out.println("matchResult : " + matchResult);
		//1234 -> 암호화
		//$2a$10$45xa/tBkJbux1/zNk8r8t.xxrEpvsEviZGIfc6z9clbiVNVJR99GS
	}
}
