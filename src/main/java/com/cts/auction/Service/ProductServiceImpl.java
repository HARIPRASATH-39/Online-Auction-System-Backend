package com.cts.auction.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.auction.DisplayDTO.ProductDisplayDTO;
import com.cts.auction.Entity.CategoryEntity;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Exception.CategoryNotFoundException;
import com.cts.auction.Exception.ProductNotFoundException;
import com.cts.auction.Exception.UserNotFoundException;
import com.cts.auction.Repository.CategoryRepository;
import com.cts.auction.Repository.ProductRepository;
import com.cts.auction.Repository.UserRepository;
import com.cts.auction.Validation.ProductDTO;
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	public ProductDisplayDTO addProduct(ProductDTO productDto, int id, MultipartFile imageFile) throws IOException {
        logger.info("Attempting to add product for user ID: {}", id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found with Id " + id));

        CategoryEntity category = categoryRepository.findByCategoryName(productDto.getCategory().getCategoryName());
        if (category == null) {
            throw new CategoryNotFoundException("Category Not Found, Product cannot be added in " + productDto.getCategory().getCategoryName() + " category");
        }

        // Set image metadata and data
        productDto.setImageName(imageFile.getOriginalFilename());
        productDto.setImageType(imageFile.getContentType());
        productDto.setImageData(imageFile.getBytes());

        ProductEntity product = ProductEntity.builder()
                .productName(productDto.getProductName())
                .price(productDto.getPrice())
                .user(user)
                .category(category)
                .imageName(productDto.getImageName())
                .imageType(productDto.getImageType())
                .imageData(productDto.getImageData())
                .build();

        productRepository.save(product);

        logger.info("Product added successfully for user ID: {}", id);

        return ConvertToProductDisplay(product);
    }


	public ProductDisplayDTO findProduct(int id) {
		
		logger.info("Attempting to find product with ID: {}", id);

		ProductEntity product=productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("No Product by ID: " + id));
		
		ProductDisplayDTO productDisplay=ConvertToProductDisplay(product);
		
		return productDisplay; 
	}

	private ProductDisplayDTO ConvertToProductDisplay(ProductEntity product) {
	
		ProductDisplayDTO productdisplay =new ProductDisplayDTO(
				product.getId(), 
				product.getProductName(), 
				product.getPrice(),
				product.getCategory().getCategoryName(),
				product.getUser().getId(),
				product.getUser().getUsername(),
				product.getHighest_bid(),
				product.getStatus()
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

	@Override
	public List<ProductDisplayDTO> getProductByCategory(String category) {

		List<ProductEntity>productList=productRepository.findByCategory_CategoryName(category);
		List<ProductDisplayDTO> productDTOList=new ArrayList<>();
		
		for(ProductEntity product:productList)
		{
		ProductDisplayDTO productDTO=ConvertToProductDisplay(product);
		productDTOList.add(productDTO);
		}
		return productDTOList;
		}


	
}
