package com.cts.auction.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cts.auction.DisplayDTO.AuctionDisplayDTO;
import com.cts.auction.Entity.AuctionEntity;
import com.cts.auction.Service.AuctionService;

@RestController
@RequestMapping("/auction/bid")
public class AuctionController {
	
	@Autowired
	AuctionService auctionService;
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping("/{id}/{pid}")
	public String placeBid(@PathVariable int id,@PathVariable int pid,@RequestBody AuctionEntity auction)
	{
		return auctionService.placeBid(id,pid,auction);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getall")
	public List<AuctionDisplayDTO> getauction()
	{
		
		return auctionService.getAllAuctions();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/get/{id}")
	public AuctionDisplayDTO getauctionbyId(@PathVariable int id) {
		return auctionService.getauctionbyId(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/delete/{id}")
	
	public String deleteAuction(@PathVariable int id)
	{
		return auctionService.deleteAuction(id);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/deleteAll")
	public String DeleteAll() {
		return auctionService.deleteAll();
		
		
		
	}
}
