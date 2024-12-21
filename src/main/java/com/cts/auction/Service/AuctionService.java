package com.cts.auction.Service;

import com.cts.auction.Entity.AuctionEntity;

public interface AuctionService {
	
	
	String placeBid(int id, int pid, AuctionEntity auction); 
	
	void scheduleAuctionEnd(int id); 
	
	void endAuction(int id);

}
