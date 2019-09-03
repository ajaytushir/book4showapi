package org.catalystone.bookurshow.service;

import org.catalystone.bookurshow.config.security.SecurityUser;
import org.catalystone.bookurshow.config.security.UserAuthentication;
import org.catalystone.bookurshow.dao.IUserRepository;
import org.catalystone.bookurshow.domain.User;
import org.catalystone.bookurshow.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAuthentication userAuthentication;
	
	public UserModel register(UserModel userModel) {
		User user = userModel.getDomain();
		
		// Encrypt the Password
		String encryptedPassword = passwordEncoder.encode(userModel.getPassword());
		user.setPassword(encryptedPassword);
		
		if(userAuthentication.getRoles()!=null && userAuthentication.getRoles().contains(SecurityUser.ADMIN_ROLE) && userModel.getIsAdmin()) {
			user.setIsAdmin(true);
		}
		
		user = userRepository.save(user);
		return UserModel.getInstance(user);
	}
}
