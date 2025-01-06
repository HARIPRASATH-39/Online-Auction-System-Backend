package com.cts.auction.DisplayDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDisplayDTO {
	
	
	private int productId;
	
	private String productName;
	
	private Double price;
	
	private String category;
	
	private int userId;
	
	private String userName;
	
	private Double highest_bid;
	
	private String status;
	
	

}
