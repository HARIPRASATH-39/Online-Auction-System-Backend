package com.cts.auction.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.auction.Entity.AuctionEntity;
import com.cts.auction.Service.AuctionService;

@RestController
@RequestMapping("/auction")
public class AuctionController {
	
	@Autowired
	AuctionService auctionService;

	@PostMapping("/{id}/{pid}")
	public String placeBid(@PathVariable int id,@PathVariable int pid,@RequestBody AuctionEntity auction)
	{
		return auctionService.placeBid(id,pid,auction);
	}
}
