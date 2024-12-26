package com.cts.auction.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.auction.DisplayDTO.AuctionDisplayDTO;
import com.cts.auction.Entity.AuctionEntity;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Exception.AuctionNotFoundException;
import com.cts.auction.Exception.UserNotFoundException;
import com.cts.auction.Repository.AuctionRepository;
import com.cts.auction.Repository.ProductRepository;
import com.cts.auction.Repository.UserRepository;
@Service
public class AuctionServiceImpl implements AuctionService{

	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	
	private static final Logger logger = LoggerFactory.getLogger(AuctionServiceImpl.class);

	
	private final ConcurrentHashMap<Integer,AuctionEntity> activeAuctions=new ConcurrentHashMap<>();
	
	public String placeBid(int id, int pid, AuctionEntity auction) {
		
		logger.info("Attempting to place a bid for user ID: {} on product ID: {}", id, pid);
		
		UserEntity user=userRepository.findById(id).get();
		
		ProductEntity product=productRepository.findById(pid).get();
		
		if(product.getStatus().equals("unsold"))
		{
		
		 if(!auction.isStatus())
		{
			 logger.warn("Auction ended already for product ID: {}", pid);
			 
			return "Auction ended Already";
		}
		
		else if(auction.getAmount()>user.getWallet_amount())
		{
			logger.warn("Entered amount is greater than the wallet amount for user ID: {}", id);
			
			return "Entered amount is greater than the remaining wallet amount";
		}
		
		else if(auction.getAmount()<=product.getHighest_bid())
		{
			logger.warn("Bid should be greater than the current bid for product ID: {}", pid);
			
			return "Bid should be greater than the current bid";
		}
		else if(auction.getAmount()<=product.getPrice())
		{
			logger.warn("Bid should be greater than the product's price for product ID: {}", pid);
			
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
			
			logger.info("Bid placed successfully for user ID: {} on product ID: {}", id, pid);
			
			return "Bid Placed Successfully";
	}
		}
		else {
			logger.warn("The product ID: {} is already sold", pid);
			
			return "The product is sold already";
		}
	}

	public void scheduleAuctionEnd(int id) {
		logger.info("Schedule auction");
		Timer timer=new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run()
			{
				endAuction(id);
			}
		},120000);
	}
	
	public void endAuction(int id)
	{
		logger.info("Entered End auction");
		

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
		
		logger.info("Auction ended ");
		
		System.out.println("auction Ended");

		
		
	}

	@Override
	public List<AuctionDisplayDTO> getAllProducts() {
		
		logger.info("Fetching All auctions");


		List<AuctionEntity> auctionList=auctionRepository.findAll();
		
		List<AuctionDisplayDTO> auctiondisplayList=new ArrayList<>();
		
		for(AuctionEntity auction:auctionList)
		{
			auctiondisplayList.add(ConvertToAuctionDisplay(auction));
			
		}
		
		
		return auctiondisplayList;
	}

	private AuctionDisplayDTO ConvertToAuctionDisplay(AuctionEntity auction) {
		
		
		
		AuctionDisplayDTO auctionDisplayDTO=new AuctionDisplayDTO(auction.getId(), auction.getUser().getId(),auction.getUser().getUsername(), auction.getProduct().getId(),auction.getProduct().getProductName(), auction.getAmount());
		
		return auctionDisplayDTO;
	}

	@Override
	public AuctionDisplayDTO getauctionbyId(int id) {
		
		logger.info("Fetching Auction By Id {}",id);

		
		AuctionEntity auction=auctionRepository.findById(id).orElseThrow(() -> new AuctionNotFoundException("No auction by ID: " + id));
		
		AuctionDisplayDTO auctiondisplay=ConvertToAuctionDisplay(auction);
		
		return auctiondisplay;
	}

}
