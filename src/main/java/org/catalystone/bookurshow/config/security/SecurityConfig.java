package org.catalystone.bookurshow.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and().exceptionHandling()
				.authenticationEntryPoint(getEntryPoint())
				.and()
				.authorizeRequests()
				// Anonymous User accessible URIs
				.antMatchers("/movieTheatre/list", "/movie/list", "/movieSchedule/list", "/user/register", "/logout")
				.permitAll()
				// Logged in user only accessible URIs
				.antMatchers("/booking/add", "/booking/list").authenticated()
				// Admin only accessible URIs
				.antMatchers("/movietheatre/add", "/movietheatre/update", "/movietheatre/delete/**", "/movie/add",
						"movieSchedule/add", "movieSchedule/listStream", "/movieSchedule/batch")
				.hasAuthority("ADMIN")

				.and().formLogin().failureHandler(failureHandler()).successHandler(successHandler()).and().logout()
				.logoutUrl("/logout").deleteCookies("JSESSIONID").logoutSuccessHandler(logoutHandler()).and().cors()
				.and().csrf().disable();

	}

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Bean
	public RestAuthenticationEntryPoint getEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}
}
