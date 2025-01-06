package com.cts.auction.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.auction.Entity.CategoryEntity;
import com.cts.auction.Service.CategoryService;

@RestController
@RequestMapping("auction/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;

	
	@GetMapping("/getAllCategory")
	@PreAuthorize("hasAuthority('SELLER') or hasAuthority('USER')")
	public List<CategoryEntity> getAllCategories()
	{
		return categoryService.getAllCategories();
	}
	
	
	
	@PostMapping("/addNewCategory")
	@PreAuthorize("hasAuthority('ADMIN')")

	public String AddCategory(@RequestBody CategoryEntity category)
	{
		
		return categoryService.AddCategory(category);
	}
	
	
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void DeleteCategory(@PathVariable int id)
	{
		categoryService.DeleteCategory(id);
	}
	
	
	
	@GetMapping("/getCategory/{id}")
	@PreAuthorize("hasAuthority('SELLER') or hasAuthority('USER')")
	public CategoryEntity getCategoryById(@PathVariable int id)
	{
		return categoryService.getCategoryById(id);
	}
	
}
