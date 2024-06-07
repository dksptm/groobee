package com.samjo.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private AuthenticationFailureHandler customFailureHandler;
	
	// 암호화처리 부분
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 인증 및 인가 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers("/assets/**", "/js/**").permitAll() // 정적 리소스 접근 허용
//								 인가경로			인가대상
				.antMatchers("/", "/introduce").permitAll()
				.antMatchers("/sol/**").hasAuthority("1C1c") //솔루션 계정 접근가능
				.antMatchers("/cust/admin/**").hasAnyAuthority("1C2c","1C3c") //고객사 계정(관리자,부서장) 접근가능
				.antMatchers("/cust/**").hasAnyAuthority("1C2c","1C3c","1C4c") //고객사 계정(전부) 접근가능
				.anyRequest().authenticated()
//						모든경로대상
				/*
				 * permitAll -> 모든대상허용, hasRole -> 특정롤요구, hasAnyrole -> 복수의 특정롤요구(LIKE),
				 */
				.and()
					.formLogin((formLogin) -> {
						formLogin.loginPage("/loginPage").permitAll();
						formLogin.loginProcessingUrl("/login");
						//formLogin.failureHandler(customFailureHandler);
						formLogin.defaultSuccessUrl("/home", true);
					});
				//.and()
		http.logout().permitAll()
			.logoutSuccessUrl("/");
		http.csrf();
		http.headers().frameOptions().sameOrigin();
		//.addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM https://permitted-site.example.com"));

		//http.exceptionHandling()
		//.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        
		
		return http.build();
	}
	
}
