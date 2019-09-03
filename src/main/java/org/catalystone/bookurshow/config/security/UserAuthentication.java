package org.catalystone.bookurshow.config.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthentication {
	private Authentication authentication;

    public String getLoggedInUser() {
    	authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication.getPrincipal()!=null) {
    		return authentication.getName();
    	}
        return null;
    }
    
    public Collection<? extends GrantedAuthority> getRoles() {
    	authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication.getPrincipal()!=null) {
    		return authentication.getAuthorities();
    	}
        return null;
    }
}
