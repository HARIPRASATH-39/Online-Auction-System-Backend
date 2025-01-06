package com.cts.auction.Validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
public class UserDTO {
	
	@NotNull(message = "Username should not be empty")
	private String username;
	
	@Email(message="Email should be in ***@***.com")
	private String email;
	
	@NotNull(message="Password should not be Empty")
	private String password;
	
	private Double wallet_amount;
	
	private String roles;
}
