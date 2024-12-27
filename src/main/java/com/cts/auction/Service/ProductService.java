package com.cts.auction.Service;

import java.util.List;

import com.cts.auction.DisplayDTO.ProductDisplayDTO;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Validation.ProductDTO;

public interface ProductService {
	
	
	String addProduct(ProductDTO productDto, int id); 
	
	ProductDisplayDTO findProduct(int id);
	
	List<ProductDisplayDTO> findAllProducts(); 
	
	String deleteById(int id); 
	
	List<ProductDisplayDTO> getproducts(int id);
	
}
