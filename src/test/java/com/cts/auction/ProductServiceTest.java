package com.cts.auction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.auction.DisplayDTO.ProductDisplayDTO;
import com.cts.auction.Entity.ProductEntity;
import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Exception.ProductNotFoundException;
import com.cts.auction.Exception.UserNotFoundException;
import com.cts.auction.Repository.ProductRepository;
import com.cts.auction.Repository.UserRepository;
import com.cts.auction.Service.ProductServiceImpl;
import com.cts.auction.Validation.ProductDTO;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private UserEntity userEntity;
    private ProductEntity productEntity;
    private ProductDTO productDTO;
    private ProductDisplayDTO productDTOdisplay;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .username("testuser")
                .email("testuser@example.com")
                .wallet_amount(100.0)
                .build();

        productEntity = ProductEntity.builder()
                .productName("Test Product")
                .price(50.0)
                .user(userEntity)
                .build();

        productDTO = ProductDTO.builder()
                .productName("Test Product")
                .price(50.0)
                .build();
        productDTOdisplay=ProductDisplayDTO.builder()
        		.productName("Test Product")
        		.price(50.0)
        		.build();
    }

    @Test
    void testAddProduct_Success() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        String result = productService.addProduct(productDTO, 1);

        assertEquals("Product added successfully", result);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void testAddProduct_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productService.addProduct(productDTO, 1);
        });

        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    @Test
    void testFindProduct_Success() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(productEntity));

        ProductDisplayDTO result = productService.findProduct(1);

        assertNotNull(result);
        assertEquals(productDTOdisplay, result);
    }

    @Test
    void testFindProduct_NotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productService.findProduct(1);
        });
    }

    @Test
    void testFindAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(productEntity));

        List<ProductEntity> result = productService.findAllProducts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(productEntity, result.get(0));
    }

    @Test
    void testDeleteById() {
        doNothing().when(productRepository).deleteById(anyInt());

        String result = productService.deleteById(1);

        assertEquals("Product 1 has been deleted", result);
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetProducts_Success() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));
        when(productRepository.findByUser(any(UserEntity.class))).thenReturn(Arrays.asList(productEntity));

        List<ProductEntity> result = productService.getproducts(1);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(productEntity, result.get(0));
    }

    @Test
    void testGetProducts_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            productService.getproducts(1);
        });

        verify(productRepository, never()).findByUser(any(UserEntity.class));
    }
}
