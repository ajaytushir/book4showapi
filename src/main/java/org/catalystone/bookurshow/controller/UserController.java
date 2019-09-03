package org.catalystone.bookurshow.controller;

import javax.validation.Valid;

import org.catalystone.bookurshow.model.UserModel;
import org.catalystone.bookurshow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public UserModel register(@RequestBody @Valid UserModel userModel) {
		return userService.register(userModel);
	}
	
}
