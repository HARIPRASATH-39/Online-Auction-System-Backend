package com.cts.auction.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.auction.DisplayDTO.AuctionDisplayDTO;
import com.cts.auction.Entity.AuctionEntity;
import com.cts.auction.Entity.UserEntity;

public interface AuctionRepository extends JpaRepository<AuctionEntity, Integer> {

	List<AuctionEntity> findByUser(UserEntity user);

}
