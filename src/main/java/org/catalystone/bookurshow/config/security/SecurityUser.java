package org.catalystone.bookurshow.config.security;

import java.util.Arrays;
import java.util.Collection;

import org.catalystone.bookurshow.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SecurityUser implements UserDetails{
	private User user;
	
	public static final GrantedAuthority ADMIN_ROLE = new GrantedAuthority() {
		@Override
		public String getAuthority() {
			return "ADMIN";
		}
	};
	
	public static final GrantedAuthority USER_ROLE = new GrantedAuthority() {
		@Override
		public String getAuthority() {
			return "USER";
		}
	};
	
    public SecurityUser(User user) {
        this.user = user;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(user.getIsAdmin()!=null && user.getIsAdmin()) {
			return Arrays.asList(USER_ROLE, ADMIN_ROLE);
		} else {
			return Arrays.asList(USER_ROLE);
		}
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String toString() {
		return "SecurityUser [user=" + user + ", getAuthorities()=" + getAuthorities() + ", getUsername()="
				+ getUsername() + "]";
	}
	
	
}
