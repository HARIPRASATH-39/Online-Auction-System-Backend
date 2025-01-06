package com.cts.auction.Service;

import java.util.List;


import com.cts.auction.Entity.CategoryEntity;


public interface CategoryService {

	List<CategoryEntity> getAllCategories();

	String AddCategory(CategoryEntity category);

	void DeleteCategory(int id);

	CategoryEntity getCategoryById(int id);
	
	

}
