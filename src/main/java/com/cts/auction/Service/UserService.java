package com.cts.auction.Service;

import java.util.List;

import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Validation.UserDTO;

public interface UserService {

	String signup(UserDTO userdto); 
	
	String login(UserEntity user); 
	
	List<UserEntity> findAllUsers(); 
	
	UserEntity findUserById(int id); 
	
	String addAmount(int id, Double amount); String deleteUser(int id);
}
