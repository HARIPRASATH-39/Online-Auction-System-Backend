package com.cts.auction.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Entity.UserEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	
	List<ProductEntity> findByUser(UserEntity user);



}
