package com.samjo.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	// 암호화처리 부분
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 인증 및 인가 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
//								 인가경로			인가대상
				.antMatchers("/", "/introduce").permitAll()
//				.antMatchers("/user/**").hasRole("USER") //ROLE_USER
//				.antMatchers("/user/**").hasAnyRole("USER", "ADMIN") // ROLE_USER
//				.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated()
//						모든경로대상
				/*
				 * permitAll -> 모든대상허용, hasRole -> 특정롤요구, hasAnyrole -> 복수의 특정롤요구(LIKE),
				 */
				.and().formLogin().defaultSuccessUrl("/").and().logout().logoutSuccessUrl("/");

//			http.formLogin().defaultSuccessUrl("/all");
//			http.logout();
		http.csrf();// .disable();

		return http.build();
	}
}
