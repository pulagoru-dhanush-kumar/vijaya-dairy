package com.uday.vijayadairy.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uday.vijayadairy.model.User;
import com.uday.vijayadairy.repository.UserRepository;
@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repo.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        User myuser = user.get();
        System.out.println("called the myuserdetails class");
		return new MyUserDetails(myuser);
	}

}
