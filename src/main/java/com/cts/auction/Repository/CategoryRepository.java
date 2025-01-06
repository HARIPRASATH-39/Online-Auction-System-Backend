package com.cts.auction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.auction.Entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{

	CategoryEntity findByCategoryName(String categoryName);

}