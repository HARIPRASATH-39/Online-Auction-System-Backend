package com.cts.auction.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="auction")
public class AuctionEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name="product_id")
	private ProductEntity product;
	
	private double amount;
	
	@ManyToOne
    @JoinColumn(name="user_id")
	private UserEntity user;
	
	private boolean status=true;
	
	
	
	

}
