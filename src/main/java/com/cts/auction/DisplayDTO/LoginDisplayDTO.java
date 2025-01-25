package com.cts.auction.DisplayDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDisplayDTO {
	
	
	
    private int id;
	
	private String username;
	
	private String token;
	
	private String roles;

}
