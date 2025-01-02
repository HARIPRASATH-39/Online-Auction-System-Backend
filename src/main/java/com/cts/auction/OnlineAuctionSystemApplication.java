package com.cts.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OnlineAuctionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineAuctionSystemApplication.class, args);
	}

}
