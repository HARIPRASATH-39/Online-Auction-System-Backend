package com.cts.auction.DisplayDTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDisplayDTO {
	
	private int id;
	
	private int userId;
	
	private String userName;
	

	private int productId;
	
	private String productName;
	
	private double amount;
	
	
	

}
