package com.cts.auction.Validation;

import com.cts.auction.Entity.CategoryEntity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	
	@NotNull(message = "Product should not be empty")
	private String productName;
	
	
	
	@Min(value=1 ,message = "Price should be greater than 1")
	@NotNull(message="Price should not be empty")
	private Double price;
	
	
	
	@NotNull(message="Category should not be empty")
	private CategoryEntity category;
	
	
	

}
