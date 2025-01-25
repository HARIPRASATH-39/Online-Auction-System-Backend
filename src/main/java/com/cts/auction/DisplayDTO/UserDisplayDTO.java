package com.cts.auction.DisplayDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDisplayDTO {

	
	private int id;
	
	private String username;
	
	private String email;
	
	private String roles;
	
	private Double wallet_amount;
	
	
}
