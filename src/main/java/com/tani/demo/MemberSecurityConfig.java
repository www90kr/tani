package com.tani.demo;

import javax.sql.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.*;

import com.tani.demo.security.ManagerAccessDeniedHandler;
import com.tani.demo.security.ManagerLoginFailureHandler;
import com.tani.demo.security.ManagerLoginSuccessHandler;
import com.tani.demo.security.*;

@EnableWebSecurity
public class MemberSecurityConfig {
	// 관리자는 /adim/**으로 접근한다
	@Order(1)
	@Configuration
	public static class AdminSecurityConfing extends WebSecurityConfigurerAdapter {
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("system").password(passwordEncoder.encode("1234")).roles("ADMIN");
		}	
		
		// 파라미터 http는 스프링 시큐리티를 이용한 접근 통제 정보를 저장할 객체
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정
			http.authorizeRequests().antMatchers("/admin/login").permitAll();
			
			// /admin/**로 들어오는 요청에 대해 ADMIN 권한과 폼 로그인 설정
			http.requestMatchers().antMatchers("/admin/**").and().authorizeRequests().anyRequest().hasRole("ADMIN")
				.and().formLogin().loginPage("/admin/login").loginProcessingUrl("/admin/login")
				.and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/").invalidateHttpSession(true);
		}
	}
	
	
	
	
	
	// 관리자가 아니면 @PreAuthorize, @Secured 어노테이션 기반으로 접근 통제
	@Order(2)
	@Configuration
//	@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
	public static class managerSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private DataSource managerdataSource;
		// 로그인에 실패했을 때 뒷처리 -> 로그인실패횟수증가, 5회 실패하면 블록
		@Autowired
		private ManagerLoginFailureHandler managerLoginFailureHandler;

		// 로그인에 성공했을 때 뒷처리
		// 로그인 실패 횟수 리셋, 임시비밀번호로 로그인하면 비밀번호 변경창으로 이동
		@Autowired
		private ManagerLoginSuccessHandler managerLoginSuccessHandler;
		
		// 권한이 없을 때 뒷처리(403)
		// 403은 컨트롤러 어드바이스가 아니라 스프링 시큐리티가 처리
		@Autowired
		private ManagerAccessDeniedHandler MaccessDeniedHandler;
		

		// 아이디, 비밀번호, enabled를 읽어오는 sql
		// 아이디와 권한 읽어오는 sql
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(managerdataSource)
				.usersByUsernameQuery("select username, spassword, enabled from manager where username=?") //db에서 아이디, 비번 비교
				.authoritiesByUsernameQuery("select username, role from manager where username=?");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정 
			http.authorizeRequests().antMatchers("/manager/login","/manager/join", "/manager/check/id", "/manager/check/email", "/manager/check/businessNo"
					, "/manager/check/tel", "/manager/findBysId", "/manager/find/username", "/manager/reset/password", "/manager/new", "/manager/findBysId", "/manager/resetPassword"
					
					).permitAll();
			//post 방식 오류 방지
			http.csrf().disable();
			http.requestMatchers().antMatchers("/manager/**").and().authorizeRequests().anyRequest().hasRole("MANAGER")
				.and().exceptionHandling().accessDeniedHandler(MaccessDeniedHandler)
				.and().formLogin().loginPage("/manager/login").loginProcessingUrl("/manager/login").successHandler(managerLoginSuccessHandler).failureHandler(managerLoginFailureHandler)
				.and().logout().logoutUrl("/manager/logout").logoutSuccessUrl("/anony/acc").invalidateHttpSession(true);

	    }
	
	       
	    } 
	
	
	
	
	
	
	
	
	
	
	// 관리자가 아니면 @PreAuthorize, @Secured 어노테이션 기반으로 접근 통제
	@Order(3)
	@Configuration
	//@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
	public static class memberSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private DataSource memberdataSource;
		// 로그인에 실패했을 때 뒷처리 -> 로그인실패횟수증가, 5회 실패하면 블록
		@Autowired
		private MemberLoginFailureHandler memberLoginFailureHandler;

		// 로그인에 성공했을 때 뒷처리
		// 로그인 실패 횟수 리셋, 임시비밀번호로 로그인하면 비밀번호 변경창으로 이동
		@Autowired
		private MemberLoginSuccessHandler memberLoginSuccessHandler;
		
		// 권한이 없을 때 뒷처리(403)
		// 403은 컨트롤러 어드바이스가 아니라 스프링 시큐리티가 처리
		@Autowired
		private MemberAccessDeniedHandler BaccessDeniedHandler;
		

		// 아이디, 비밀번호, enabled를 읽어오는 sql
		// 아이디와 권한 읽어오는 sql
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(memberdataSource)
				.usersByUsernameQuery("select username, password, enabled from member where username=?") //db에서 아이디, 비번 비교
				.authoritiesByUsernameQuery("select username, role from member where username=?");
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정
						http.authorizeRequests().antMatchers("/member/new","/member/login","/member/join", "/member/check/id", "/member/check/email",
								"/member/check/nickname","/member/check/tel","/member/findById", "/member/find/username",  "/member/reset/password","/member/resetPassword").permitAll();
			http.csrf().disable();
			http.requestMatchers().antMatchers("/member/**").and().authorizeRequests().anyRequest().hasRole("MEMBER")
			.and().exceptionHandling().accessDeniedHandler(BaccessDeniedHandler)
				.and().formLogin().loginPage("/member/login").loginProcessingUrl("/member/login").successHandler(memberLoginSuccessHandler).failureHandler(memberLoginFailureHandler)
				.and().logout().logoutUrl("/member/logout").logoutSuccessUrl("/anony/acc").invalidateHttpSession(true);
		}
	
	}
	
	
	// 관리자가 아니면 @PreAuthorize, @Secured 어노테이션 기반으로 접근 통제
	@Order(4)
	@Configuration
	//@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
	public static class comSecurityConfig extends WebSecurityConfigurerAdapter {
		
		
		// 파라미터 http는 스프링 시큐리티를 이용한 접근 통제 정보를 저장할 객체
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정
			http.authorizeRequests().antMatchers("/anony/**" ).permitAll();
			http.csrf().disable();
			// /admin/**로 들어오는 요청에 대해 ADMIN 권한과 폼 로그인 설정
			
		}
		
	}
	
	
	
	
	
	
	
	}
