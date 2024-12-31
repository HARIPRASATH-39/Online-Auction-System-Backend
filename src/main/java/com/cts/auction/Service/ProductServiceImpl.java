package com.cts.auction.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.auction.DisplayDTO.ProductDisplayDTO;
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

	public ProductDisplayDTO findProduct(int id) {
		
		logger.info("Attempting to find product with ID: {}", id);

		ProductEntity product=productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("No Product by ID: " + id));
		
		ProductDisplayDTO productDisplay=ConvertToProductDisplay(product);
		
		return productDisplay; 
	}

	private ProductDisplayDTO ConvertToProductDisplay(ProductEntity product) {
	
		ProductDisplayDTO productdisplay =new ProductDisplayDTO(
				product.getId(), product.getProductName(), product.getPrice(),
				product.getUser().getId(),product.getUser().getUsername(),
				product.getHighest_bid(), product.getStatus()
				);
		return productdisplay;
	}

	public List<ProductDisplayDTO> findAllProducts() {
		
		logger.info("Fetching all products");
		List<ProductEntity> productList=productRepository.findAll();
		
		List<ProductDisplayDTO> productDTOList=new ArrayList<>();
		
		for(ProductEntity product:productList)
		{
		ProductDisplayDTO productDTO=ConvertToProductDisplay(product);
		productDTOList.add(productDTO);
		}
		return productDTOList;
		}
	

	public String deleteById(int id) {
		
		logger.info("Attempting to delete product with ID: {}", id);
		
		ProductEntity product=productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("No Product by ID: " + id));
		
		productRepository.deleteById(id);
		
		logger.info("Product {} has been deleted", id);
		
		return "Product "+id+" has been deleted";
		
	}

	public List<ProductDisplayDTO> getproducts(int id) {
		
		logger.info("Fetching products for user ID: {}", id);
		
		UserEntity user=userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
		List<ProductEntity> productList =productRepository.findByUser(user);
		
		List<ProductDisplayDTO> productDTOList=new ArrayList<>();
		
		for(ProductEntity product:productList)
		{
		ProductDisplayDTO productDTO=ConvertToProductDisplay(product);
		productDTOList.add(productDTO);
		}
		return productDTOList;
		}

	@Override
	public String deleteAll() {
		
		productRepository.deleteAll();
		
		return "Deleted All Products";
	}

}
