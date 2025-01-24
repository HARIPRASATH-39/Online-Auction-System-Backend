package com.cts.auction.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.auction.DisplayDTO.ProductDisplayDTO;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Repository.ProductRepository;
import com.cts.auction.Validation.ProductDTO;

public interface ProductService {
	
	
	ProductDisplayDTO addProduct(ProductDTO productDto, int id, MultipartFile imageFile) throws IOException; 
	
	ProductDisplayDTO findProduct(int id);
	
	List<ProductDisplayDTO> findAllProducts(); 
	
	String deleteById(int id); 
	
	List<ProductDisplayDTO> getproducts(int id);

	
	String deleteAll();

	List<ProductDisplayDTO> getProductByCategory(String category);

	
}
