package com.cts.auction.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.auction.Entity.CategoryEntity;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Exception.CategoryNotFoundException;
import com.cts.auction.Repository.CategoryRepository;
import com.cts.auction.Repository.ProductRepository;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public List<CategoryEntity> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public String AddCategory(CategoryEntity category) {
		
		CategoryEntity category1= categoryRepository.findByCategoryName(category.getCategoryName());
		
		if(category1!=null)
		{
			throw new RuntimeException("Category Already Exists");
		}
		
		categoryRepository.save(category);
		
		return "Category Added Successfully";
	}

	@Override
	public void DeleteCategory(int id) {
		
		CategoryEntity category=categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("No Category by ID: " + id));
		
		List<ProductEntity> products=productRepository.findByCategory_CategoryName(category.getCategoryName());

		if(!products.isEmpty())
		{
			throw new RuntimeException("Cannot able to delete the Category... It contains some products");
		}
		
		categoryRepository.deleteById(id);
	}

	@Override
	public CategoryEntity getCategoryById(int id) {
		
		return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("No Category by ID: " + id));
	}
	
	
	
	

}
