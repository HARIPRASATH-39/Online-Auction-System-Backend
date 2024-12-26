package com.cts.auction.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cts.auction.DisplayDTO.UserDisplayDTO;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Service.UserService;
import com.cts.auction.Validation.UserDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auction/user")
public class UserController {
	
	@Autowired
	UserService userService;
	


	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/signup")
	public String signup(@Valid @RequestBody UserDTO userdto)
	{
		
			return userService.signup(userdto);
		
	}
	
	@PostMapping("/login")
	public String login(@RequestBody UserEntity user) {
		return userService.login(user);
	}
	
	@GetMapping("/listAllUsers")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<UserDisplayDTO> findAllUsers()
	{
		return userService.findAllUsers();
	}
	
	@GetMapping("/find/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public UserDisplayDTO findUserById(@PathVariable int id)
	
	{
		
		return userService.findUserById(id);
	}
	
	@PutMapping("/addAmount/{id}/{amount}")
	public String addAmount(@PathVariable int id,@PathVariable Double amount)
	{
		return userService.addAmount(id,amount);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String deleteUser(@PathVariable int id)
	{
		return userService.deleteUser(id);
	}
	
	
	
}
