package org.catalystone.bookurshow.model;

import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotEmpty;

import org.catalystone.bookurshow.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserModel {
	private Long id;

	@NotEmpty
	private String email;
	private String password;
	private Boolean isAdmin;
	private String confirmPassword;
	private String created;
	
	@JsonIgnore
	public User getDomain() {
		User user = new User();
		user.setEmail(this.email);
		user.setId(this.id);
		user.setIsAdmin(this.getIsAdmin());
		return user;
	}
	
	public static UserModel getInstance(User user) {
		UserModel userModel = new UserModel();
		userModel.setId(user.getId());
		userModel.setEmail(user.getEmail());
		userModel.setCreated(user.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		userModel.setIsAdmin(user.getIsAdmin());
		return userModel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
}
