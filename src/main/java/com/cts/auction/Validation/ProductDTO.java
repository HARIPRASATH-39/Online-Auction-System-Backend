package com.cts.auction.Validation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
	
	@NotBlank(message = "Product should not be empty")
	private String productName;
	
	@Min(value=1 ,message = "Price should be greater than 1")
	@NotNull(message="Price should not be empty")
	private Double price;
	

}
