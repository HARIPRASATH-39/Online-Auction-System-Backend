package com.cts.auction.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.auction.DisplayDTO.UserDisplayDTO;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Exception.UserNotFoundException;
import com.cts.auction.Repository.UserRepository;
import com.cts.auction.Validation.UserDTO;
import org.slf4j.Logger; 
import org.slf4j.*;
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncode;
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

		
	public String signup(UserDTO userdto) {
		
		logger.info("Attempting to sign up user with email: {}",userdto.getEmail());
				
		UserEntity dbPerson = userRepository.findByEmail(userdto.getEmail());
		boolean user_already_registered = dbPerson != null ? true : false;
		if(user_already_registered)
		{
			logger.warn("The email address is already registered: {}",userdto.getEmail());
					
			throw new RuntimeException("The email address is already registered.");
		}
		else { 
			
			
			userdto.setPassword(passwordEncode.encode(userdto.getPassword()));
			
			UserEntity user = UserEntity.builder()
		            .username(userdto.getUsername())
		            .email(userdto.getEmail())
		            .password(userdto.getPassword())
		            .wallet_amount(userdto.getWallet_amount())
		            .roles(userdto.getRoles())
		        .build();
			
			userRepository.save(user);
			
		 logger.info("Account created successfully for email: {}",userdto.getEmail());
			
			return "Account created successfuly";
		
		}
		
	}

	public UserDisplayDTO login(UserEntity user) {
		
		logger.info("Attempting to login user with username: {}", user.getUsername());
		
		UserEntity dbPerson = userRepository.findByUsername(user.getUsername()).get();

		String password=dbPerson.getPassword();
		
		boolean loginStatus=passwordEncode.matches(user.getPassword(),password);
		
		if(loginStatus)
		{
			logger.info("Login successful for username: {}", user.getUsername());
			
			return ConvertToUserDisplayDTO(dbPerson);
		}
		else { 
			
			logger.warn("Login failed for username: {}", user.getUsername());
			
			throw new RuntimeException("Invalid Credentials");
		
		}
	}
	
	public List<UserDisplayDTO> findAllUsers(){
		
		logger.info("Fetching all users"); 
		List<UserEntity> userList=userRepository.findAll();
		List<UserDisplayDTO> userDTOList=new ArrayList<>();
		
		for(UserEntity user:userList)
		{
		UserDisplayDTO userDTO=ConvertToUserDisplayDTO(user);
		userDTOList.add(userDTO);
		}
		
		return userDTOList;
	}

	public UserDisplayDTO findUserById(int id) {
		
		logger.info("Fetching user with ID: {}", id);
		
		UserEntity user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
		
		UserDisplayDTO userDisplayDTO=ConvertToUserDisplayDTO(user);
		
		return userDisplayDTO;
	}

	private UserDisplayDTO ConvertToUserDisplayDTO(UserEntity user) {
		
		return new UserDisplayDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRoles(),user.getWallet_amount());
	}

	public String addAmount(int id, Double amount) {
		
		logger.info("Adding amount: {} to user with ID: {}", amount, id);
		
		Optional<UserEntity> userop=userRepository.findById(id);
		UserEntity user=userop.get();

		if(userop.isPresent())
		{
		user.setWallet_amount(user.getWallet_amount() + amount);
		userRepository.save(user);
		
		logger.info("Amount added successfully to user with ID: {}", id);
		
		return "Amount added successfull";
		}
		else {
			throw new UserNotFoundException("No user is found by the ID: "+id);
		}
		
		
	}

	public String deleteUser(int id) {
		
		logger.info("Deleting user with ID: {}", id);
		
		UserEntity user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
		userRepository.delete(user);
		
		return "User Removed";
	}


	

}
