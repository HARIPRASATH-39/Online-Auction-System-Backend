package com.cts.auction.Service;

import java.util.List;

import com.cts.auction.DisplayDTO.LoginDisplayDTO;
import com.cts.auction.DisplayDTO.UserDisplayDTO;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Validation.UserDTO;

public interface UserService {

	String signup(UserDTO userdto); 
	
	LoginDisplayDTO login(UserEntity user); 
	
	List<UserDisplayDTO> findAllUsers(); 
	
	UserDisplayDTO findUserById(int id); 
	
	String addAmount(int id, Double amount);
	
	String deleteUser(int id);

	UserDisplayDTO updateUser(int id, UserEntity user);
}
