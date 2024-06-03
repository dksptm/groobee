package com.samjo.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	//파일삭제 테스트
	//@Test
	public void deleteTest() {
		System.out.println("파일삭제 테스트 시작");
		//파일삭제1
		String testPath = uploadPath + File.separator + "10405de1-aab6-4c93-bff2-f63e67a140fd_tempPreview.png";
						//파일 경로
		File file = new File(testPath);
		//파일 상태 확인
		System.out.println("파일 경로: " + file.getAbsolutePath());
		System.out.println("파일 존재 여부: " + file.exists());
		System.out.println("읽기 가능: " + file.canRead());
		System.out.println("쓰기 가능: " + file.canWrite());
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("파일삭제 성공");
			} else {
				System.out.println("파일삭제 실패");
			}
		} else {
			System.out.println("파일이 존재하지 않습니다.");
		}

		//파일삭제2
		Path filePath = Paths.get(uploadPath + File.separator + "test.txt");
								//파일 경로
		try {
			// 파일 삭제
			Files.delete(filePath);
		} catch (NoSuchFileException e) {
			System.out.println("삭제하려는 파일/디렉토리가 없습니다");
		} catch (IOException e) {
			e.printStackTrace();
		}

        //디렉토리 존재유무 확인 및 내부파일 확인
        File dir = new File(uploadPath);
        System.out.println("절대 경로: " + dir.getAbsolutePath());

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                System.out.println("디렉토리 내의 파일 목록:");
                for (File filess : files) {
                    System.out.println(filess.getName());
                }
            } else {
                System.out.println("디렉토리가 비어 있습니다.");
            }
        } else {
            System.out.println("디렉토리가 존재하지 않습니다.");
        }
	}
	
	 public static void deleteFile(String filePath) {
	        File file = new File(filePath);
	        System.out.println("절대 경로: " + file.getAbsolutePath());
	        
	        if (file.exists()) {
	            System.out.println("파일이 존재합니다.");
	            if (file.delete()) {
	                System.out.println("파일삭제 성공");
	            } else {
	                System.out.println("파일삭제 실패");
	            }
	        } else {
	            System.out.println("파일이 존재하지 않습니다.");
	        }
	    }

	 
	 @Autowired
	 StringEncryptor jasyptStringEncryptor;
	 
	 //@Test
	 public void encryption() {
			String[] strs = {
					"",
					""
			};
			
			for(String str : strs) {
				String encStr = jasyptStringEncryptor.encrypt(str);
				System.out.println(encStr);
			}
		}
}
