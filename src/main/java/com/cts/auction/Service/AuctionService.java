package com.cts.auction.Service;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.auction.Entity.AuctionEntity;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Repository.AuctionRepository;
import com.cts.auction.Repository.ProductRepository;
import com.cts.auction.Repository.UserRepository;
@Service
public class AuctionService {

	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	private final ConcurrentHashMap<Integer,AuctionEntity> activeAuctions=new ConcurrentHashMap<>();
	
	public String placeBid(int id, int pid, AuctionEntity auction) {
		
		UserEntity user=userRepository.findById(id).get();
		
		ProductEntity product=productRepository.findById(pid).get();
		
		if(product.getStatus().equals("unsold"))
		{
		
		 if(!auction.isStatus())
		{
			return "Auction ended Already";
		}
		
		else if(auction.getAmount()>user.getWallet_amount())
		{
			return "Entered amount is greater than the remaining wallet amount";
		}
		
		else if(auction.getAmount()<=product.getHighest_bid())
		{
			return "Bid should be greater than the current bid";
		}
		else if(auction.getAmount()<=product.getPrice())
		{
			return "Bid should be greater than the product's price";
		}
		
		else {
			if(product.getHighest_bid()==0.0)
			{
				scheduleAuctionEnd(auction.getId());
			}
			product.setHighest_bid(auction.getAmount());
			
			productRepository.save(product);
			auction.setUser(user);
			auction.setProduct(product);
			activeAuctions.put(auction.getId(), auction);
			
			
			return "Bid Placed Successfully";
	}
		}
		else {
			return "The product is sold already";
		}
	}

	private void scheduleAuctionEnd(int id) {
		System.out.println("Entered Schedule auction");
		Timer timer=new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run()
			{
				endAuction(id);
			}
		},120000);
	}
	
	private void endAuction(int id)
	{
		System.out.println("Entered End auction");
		

		AuctionEntity auction=activeAuctions.get(id);
		
		if(!auction.isStatus())
		{
			return;
		}
		
		UserEntity user=auction.getUser();
		ProductEntity product=auction.getProduct();
		product.setStatus("sold");
		productRepository.save(product);
		user.setWallet_amount(user.getWallet_amount()-auction.getAmount());
		
		userRepository.save(user);
		
		auction.setStatus(false);
		
		auctionRepository.save(auction);
		
		System.out.println("auction Ended");

		
		
	}

}
