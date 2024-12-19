package com.cts.auction.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Exception.UserNotFoundException;
import com.cts.auction.Repository.UserRepository;
import com.cts.auction.Validation.UserDTO;
@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncode;

	
	
	public String signup(UserDTO userdto) {
		UserEntity dbPerson = userRepository.findByEmail(userdto.getEmail());
		boolean user_already_registered = dbPerson != null ? true : false;
		if(user_already_registered)
		{
			return "The email address is already registered.";
		}
		else { 
			
			
			userdto.setPassword(passwordEncode.encode(userdto.getPassword()));
			
			UserEntity user = UserEntity.builder()
		            .username(userdto.getUsername())
		            .email(userdto.getEmail())
		            .password(userdto.getPassword())
		            .wallet_amount(userdto.getWallet_amount())
		           // .roles(userdto.getRoles())
		        .build();
			
			userRepository.save(user);
			return "Account created successfuly";
		
		}
		
	}

	public String login(UserEntity user) {
		
		UserEntity dbPerson = userRepository.findByUsername(user.getUsername()).get();

		String password=dbPerson.getPassword();
		boolean loginStatus=passwordEncode.matches(user.getPassword(),password);
		if(loginStatus)
		{
			return "Login Successful";
		}
		else { 
			
			throw new RuntimeException("Invalid Credentials");
		
		}
	}
	
	public List<UserEntity> findAllUsers(){
		return userRepository.findAll();
	}

	public UserEntity findUserById(int id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
	}

	public String addAmount(int id, Double amount) {
		
		Optional<UserEntity> userop=userRepository.findById(id);
		UserEntity user=userop.get();

		if(userop.isPresent())
		{
		user.setWallet_amount(user.getWallet_amount() + amount);
		userRepository.save(user);
		return "Amount added successfull";
		}
		else {
			throw new UserNotFoundException("No user is found by the ID: "+id);
		}
		
		
	}

	public String deleteUser(int id) {
		UserEntity user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
		userRepository.delete(user);
		return "User Removed";
	}


	

}
