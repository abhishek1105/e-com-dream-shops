package com.freeCodeCamp.dream_shops.services.product;

import com.freeCodeCamp.dream_shops.dto.ProductDto;
import com.freeCodeCamp.dream_shops.model.Product;
import com.freeCodeCamp.dream_shops.request.AddProductRequest;
import com.freeCodeCamp.dream_shops.request.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest addProductRequest);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
