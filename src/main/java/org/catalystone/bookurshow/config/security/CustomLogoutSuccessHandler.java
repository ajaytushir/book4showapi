package org.catalystone.bookurshow.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.catalystone.bookurshow.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onLogoutSuccess(HttpServletRequest arg0, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.setStatus(HttpStatus.OK.value());
		UserModel model = new UserModel();
		response.getOutputStream().println(objectMapper.writeValueAsString(model));

	}

}
