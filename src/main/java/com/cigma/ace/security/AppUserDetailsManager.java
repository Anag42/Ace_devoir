package com.cigma.ace.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsManager implements UserDetailsManager {
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

	@Override
	public void changePassword(String arg0, String arg1) {}

	@Override
	public void createUser(UserDetails arg0) {
		
	}

	@Override
	public void deleteUser(String arg0) {}

	@Override
	public void updateUser(UserDetails arg0) {}

	@Override
	public boolean userExists(String arg0) {
		return false;
	}
}
