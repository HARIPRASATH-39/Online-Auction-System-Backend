package com.cts.auction.Config;

 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Repository.UserRepository;
 

 
@Component
public class UserUserDetailsService implements UserDetailsService {
 
	@Autowired
	private UserRepository userRepository;
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserEntity> user=userRepository.findByUsername(username);
		return user.map(UserUserDetails::new)
			.orElseThrow(()->new UsernameNotFoundException("user not found"));
	}
}