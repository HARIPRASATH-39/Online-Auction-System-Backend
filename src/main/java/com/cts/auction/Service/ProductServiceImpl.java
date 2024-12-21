package com.cts.auction.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Exception.ProductNotFoundException;
import com.cts.auction.Exception.UserNotFoundException;
import com.cts.auction.Repository.ProductRepository;
import com.cts.auction.Repository.UserRepository;
import com.cts.auction.Validation.ProductDTO;
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	public String addProduct(ProductDTO productDto, int id) {
		
		logger.info("Attempting to add product for user ID: {}", id);
		
		Optional<UserEntity> userop=userRepository.findById(id);
		UserEntity user=userop.get();
		ProductEntity product = ProductEntity.builder()
	            .productName(productDto.getProductName())
	            .price(productDto.getPrice())
	            .user(user)
	             .build();
		productRepository.save(product);
		
		logger.info("Product added successfully for user ID: {}", id);
		
		return "Product added successfully";
	}

	public ProductEntity findProduct(int id) {
		
		logger.info("Attempting to find product with ID: {}", id);

		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("No Product by ID: " + id));
	}

	public List<ProductEntity> findAllProducts() {
		
		logger.info("Fetching all products");
		
		return productRepository.findAll();
	}

	public String deleteById(int id) {
		
		logger.info("Attempting to delete product with ID: {}", id);
		
		productRepository.deleteById(id);
		
		logger.info("Product {} has been deleted", id);
		
		return "Product "+id+" has been deleted";
		
	}

	public List<ProductEntity> getproducts(int id) {
		
		logger.info("Fetching products for user ID: {}", id);
		
		UserEntity user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
		return productRepository.findByUser(user);
	}

}
