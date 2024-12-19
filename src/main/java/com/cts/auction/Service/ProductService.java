package com.cts.auction.Service;

import java.util.List;
import java.util.Optional;

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
public class ProductService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;

	public String addProduct(ProductDTO productDto, int id) {
		
		Optional<UserEntity> userop=userRepository.findById(id);
		UserEntity user=userop.get();
		ProductEntity product = ProductEntity.builder()
	            .productName(productDto.getProductName())
	            .price(productDto.getPrice())
	            .user(user)
	             .build();
		productRepository.save(product);
		return "Product added successfully";
	}

	public ProductEntity findProduct(int id) {

		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("No Product by ID: " + id));
	}

	public List<ProductEntity> findAllProducts() {
		
		return productRepository.findAll();
	}

	public String deleteById(int id) {
		productRepository.deleteById(id);
		return "Product "+id+" has been deleted";
		
	}

	public List<ProductEntity> getproducts(int id) {
		
		UserEntity user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
		return productRepository.findByUser(user);
	}

}
