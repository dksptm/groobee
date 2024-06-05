package com.samjo.app;

import java.text.ParseException;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.samjo.app.pay.service.PayService;

@SpringBootTest
class GroupwareApplicationTests {

	//@Test
	void contextLoads() {
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${file.upload.path}")
	private String uploadPath;

	//@Test
	public void testEncoder() {
		String password = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";

		String enPwd = passwordEncoder.encode(password);
		System.out.println("enPwd : " + enPwd);
//														  원래 데이터, 암호화된 데이터
		boolean matchResult = passwordEncoder.matches(password, enPwd);
		System.out.println("matchResult : " + matchResult);
		// 1234 -> 암호화
		// $2a$10$45xa/tBkJbux1/zNk8r8t.xxrEpvsEviZGIfc6z9clbiVNVJR99GS
	}

	 @Autowired
	 StringEncryptor jasyptStringEncryptor;
	 
	 //@Test
	 public void encryption() {
			String[] strs = {
					"test",
					"test"
			};
			
			for(String str : strs) {
				String encStr = jasyptStringEncryptor.encrypt(str);
				System.out.println(encStr);
			}
	}
}
