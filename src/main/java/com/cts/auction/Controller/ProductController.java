package com.cts.auction.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.auction.DisplayDTO.ProductDisplayDTO;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Exception.ProductNotFoundException;
import com.cts.auction.Repository.ProductRepository;
import com.cts.auction.Service.ProductService;
import com.cts.auction.Validation.ProductDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auction/product")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin

public class ProductController {
	
	
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductRepository productRepository;
	
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/add/user/{id}")
	@PreAuthorize("hasAuthority('SELLER')")

	public ResponseEntity<?> addProduct(
            @RequestPart("product") ProductDTO productDto,
            @RequestPart("image") MultipartFile imageFile,
            @PathVariable int id
    ) {
        try {
            ProductDisplayDTO productDisplay = productService.addProduct(productDto, id, imageFile);
            return new ResponseEntity<>(productDisplay, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/find/{id}")
	public ProductDisplayDTO  findProduct(@PathVariable int id)
	{
		return productService.findProduct(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/findAll")
	public List<ProductDisplayDTO> findAllProducts()
	{
		return productService.findAllProducts();
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('SELLER')")
	public String deleteById(@PathVariable int id)
	{
		return productService.deleteById(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("user/{id}")
	public List<ProductDisplayDTO> getproducts(@PathVariable int id)
	{
		
		return productService.getproducts(id);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/deleteAll")
	@PreAuthorize("hasAuthority('SELLER')")
	public String deleteAll()
	{
		 return productService.deleteAll();
		 
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/getByCategory/{category}")
	public List<ProductDisplayDTO> getProductByCategory(@PathVariable String category)
	{
		return productService.getProductByCategory(category);
		
	}
	
	
	@GetMapping("/image/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable int id) {
	    ProductEntity product = productRepository.findById(id)
	            .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

	    return ResponseEntity.ok()
	            .header("Content-Type", product.getImageType())
	            .body(product.getImageData());
	}
	
	
	

		
	}
	
	
	
	


