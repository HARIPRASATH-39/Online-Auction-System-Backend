package com.cts.auction.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.auction.DisplayDTO.AuctionDisplayDTO;
import com.cts.auction.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	UserEntity findByEmailAndPassword(String email, String password);
	
	Optional<UserEntity> findByUsername(String username);

	UserEntity findByEmail(String email);

	UserEntity findByUsernameAndPassword(String username, String password);

	UserEntity findByEmailOrUsername(String email, String string);


}
