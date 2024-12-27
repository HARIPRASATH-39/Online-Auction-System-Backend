package com.cts.auction.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cts.auction.DisplayDTO.ProductDisplayDTO;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Service.ProductService;
import com.cts.auction.Validation.ProductDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auction/product")
public class ProductController {
	
	
	
	@Autowired
	ProductService productService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/add/user/{id}")
	public String addProduct(@Valid @RequestBody ProductDTO productDto,@PathVariable int id)
	{
		
		return productService.addProduct(productDto,id);
	}
	
	@GetMapping("/find/{id}")
	public ProductDisplayDTO  findProduct(@PathVariable int id)
	{
		return productService.findProduct(id);
	}
	
	@GetMapping("/findAll")
	public List<ProductDisplayDTO> findAllProducts()
	{
		return productService.findAllProducts();
	}
	
	@DeleteMapping("/delete/{id}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteById(@PathVariable int id)
	{
		return productService.deleteById(id);
	}
	
	@GetMapping("user/{id}")
	public List<ProductDisplayDTO> getproducts(@PathVariable int id)
	{
		
		return productService.getproducts(id);
	}
	
	
	
	
	

}
