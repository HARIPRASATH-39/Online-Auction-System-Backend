package com.cts.auction.Service;

import java.util.concurrent.ConcurrentHashMap;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.auction.Entity.AuctionEntity;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Repository.AuctionRepository;
import com.cts.auction.Repository.ProductRepository;
import com.cts.auction.Repository.UserRepository;



@Service
public class TransactionManagementService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuctionServiceImpl.class);
	
	
	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Transactional
	public void endAuction(int id, ConcurrentHashMap<Integer, AuctionEntity> activeAuctions) 
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
		
		user.setWallet_amount(user.getWallet_amount() - auction.getAmount());
		userRepository.save(user);
		
		
		
		UserEntity seller =auction.getProduct().getUser();
		seller.setWallet_amount(seller.getWallet_amount() + auction.getAmount());
		userRepository.save(seller);
		
		auction.setStatus(false);
		
		auctionRepository.save(auction);
		
		logger.info("Auction ended ");
		
		System.out.println("Auction Ended");
		
	}

	


}
