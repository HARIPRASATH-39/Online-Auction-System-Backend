package com.cts.auction.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin

public class UserController {
	
	@Autowired
	UserService userService;
	


	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/signup")
	public String signup(@Valid @RequestBody UserDTO userdto)
	{
		
			return userService.signup(userdto);
		
	}
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/login")
	public UserDisplayDTO login(@RequestBody UserEntity user) {
		return userService.login(user);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/listAllUsers")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<UserDisplayDTO> findAllUsers()
	{
		return userService.findAllUsers();
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/find/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('SELLER')" )
	public UserDisplayDTO findUserById(@PathVariable int id)
	
	{
		
		return userService.findUserById(id);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('SELLER')" )
	@PutMapping("/addAmount/{id}/{amount}")
	public String addAmount(@PathVariable int id,@PathVariable Double amount)
	{
		return userService.addAmount(id,amount);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteUser(@PathVariable int id)
	{
		return userService.deleteUser(id);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('USER')")
	public UserDisplayDTO updateUser(@PathVariable int id,@RequestBody UserEntity user)
	{
		return userService.updateUser(id,user);
	}
	
	
	
}
