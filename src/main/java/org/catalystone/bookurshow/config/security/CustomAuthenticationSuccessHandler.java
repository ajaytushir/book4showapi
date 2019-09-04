package org.catalystone.bookurshow.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		response.setStatus(HttpStatus.OK.value());
		response.getOutputStream().println(objectMapper.writeValueAsString(authentication.getPrincipal()));
	}
}
