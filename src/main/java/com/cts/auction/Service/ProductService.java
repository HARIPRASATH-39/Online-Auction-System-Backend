package com.cts.auction.Service;

import java.util.List;

import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Validation.ProductDTO;

public interface ProductService {
	
	
	String addProduct(ProductDTO productDto, int id); 
	
	ProductEntity findProduct(int id);
	
	List<ProductEntity> findAllProducts(); 
	
	String deleteById(int id); 
	
	List<ProductEntity> getproducts(int id);
	
}
