package com.shan.app.security.admin;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.jdo.annotations.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shan.app.domain.User;
import com.shan.app.domain.UserAuthority;
import com.shan.app.error.EntityNotFoundException;
import com.shan.app.repository.admin.AdminUserRepository;
import com.shan.app.security.SecurityUser;
import com.shan.app.security.UserNotActivatedException;
import com.shan.app.service.admin.AdminUserAuthorityService;

@Service
public class AdminCustomUserDetailsService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(AdminCustomUserDetailsService.class);
	
	@Resource(name="adminUserRepository")
	private AdminUserRepository adminUserRepository;
	
	@Resource(name="adminUserAuthorityService")
	private AdminUserAuthorityService adminUserAuthorityService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		logger.debug("Authenticating {}", userId);
		
		Optional<User> userFromDatabase = adminUserRepository.findByUserId(userId);
		return userFromDatabase.map(user -> {
			if(!"Y".equals(user.getState())) {
				throw new UserNotActivatedException("User " + userId + " was not activated");
			}
			
			List<UserAuthority> userAuthoritys = adminUserAuthorityService.getUserAuthoritys(user);
			return new SecurityUser(user, userAuthoritys);
		}).orElseThrow(() -> new EntityNotFoundException(User.class, "userId", userId));
	}

}
