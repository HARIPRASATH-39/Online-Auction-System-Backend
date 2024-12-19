package com.cts.auction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.auction.Entity.AuctionEntity;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Integer> {

}
