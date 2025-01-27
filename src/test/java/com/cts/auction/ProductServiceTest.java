package com.cts.auction;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.cts.auction.Service.ProductServiceImpl;
import com.cts.auction.Validation.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private UserEntity user;
    private CategoryEntity category;
    private ProductEntity product;
    private ProductDTO productDto;
    private MultipartFile imageFile;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1);
        user.setUsername("testUser");

        category = new CategoryEntity();
        category.setCategoryName("Electronics");

        product = new ProductEntity();
        product.setId(1);
        product.setProductName("Smartphone");
        product.setPrice(500.0);
        product.setUser(user);
        product.setCategory(category);

        productDto = new ProductDTO();
        productDto.setProductName("Smartphone");
        productDto.setPrice(500.0);
        productDto.setCategory(category);

        imageFile = mock(MultipartFile.class);
    }

    @Test
    void testAddProduct() throws IOException {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(category);
        when(imageFile.getOriginalFilename()).thenReturn("smartphone.jpg");
        when(imageFile.getContentType()).thenReturn("image/jpeg");
        when(imageFile.getBytes()).thenReturn(new byte[1024]);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        ProductDisplayDTO result = productService.addProduct(productDto, 1, imageFile);

        assertNotNull(result);
        assertEquals("Smartphone", result.getProductName());
        assertEquals(500.0, result.getPrice());
        assertEquals("Electronics", result.getCategory());
        assertEquals(1, result.getUserId());
        assertEquals("testUser", result.getUserName());

        verify(userRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).findByCategoryName("Electronics");
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testAddProduct_UserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> productService.addProduct(productDto, 1, imageFile));

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testAddProduct_CategoryNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(null);

        assertThrows(CategoryNotFoundException.class, () -> productService.addProduct(productDto, 1, imageFile));

        verify(userRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).findByCategoryName("Electronics");
    }

    @Test
    void testFindProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ProductDisplayDTO result = productService.findProduct(1);

        assertNotNull(result);
        assertEquals("Smartphone", result.getProductName());
        assertEquals(500.0, result.getPrice());
        assertEquals("Electronics", result.getCategory());
        assertEquals(1, result.getUserId());
        assertEquals("testUser", result.getUserName());

        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testFindProduct_ProductNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findProduct(1));

        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testFindAllProducts() {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDisplayDTO> result = productService.findAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Smartphone", result.get(0).getProductName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        String result = productService.deleteById(1);

        assertEquals("Product 1 has been deleted", result);

        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteById_ProductNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(1));

        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testGetProducts() {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(product);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(productRepository.findByUser(user)).thenReturn(productList);

        List<ProductDisplayDTO> result = productService.getproducts(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Smartphone", result.get(0).getProductName());

        verify(userRepository, times(1)).findById(1);
        verify(productRepository, times(1)).findByUser(user);
    }

    @Test
    void testGetProducts_UserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> productService.getproducts(1));

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteAll() {
        String result = productService.deleteAll();

        assertEquals("Deleted All Products", result);

        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    void testGetProductByCategory() {
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findByCategory_CategoryName("Electronics")).thenReturn(productList);

        List<ProductDisplayDTO> result = productService.getProductByCategory("Electronics");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Smartphone", result.get(0).getProductName());

        verify(productRepository, times(1)).findByCategory_CategoryName("Electronics");
    }
}