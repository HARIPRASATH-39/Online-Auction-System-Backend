package com.cts.auction.Validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data

public class UserDTO {
	
	@NotBlank(message = "Username should not be empty")
	private String username;
	
	@Email(message="Email should be in ***@***.com")
	private String email;
	
	@NotBlank(message="Password should not be Empty")
	private String password;
	
	private Double wallet_amount;
	
	//private String roles;
}
