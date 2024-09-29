package com.freeCodeCamp.dream_shops.services.product;

import com.freeCodeCamp.dream_shops.dto.ImageDto;
import com.freeCodeCamp.dream_shops.dto.ProductDto;
import com.freeCodeCamp.dream_shops.exceptions.AlreadyExistsException;
import com.freeCodeCamp.dream_shops.exceptions.ProductNotFoundException;
import com.freeCodeCamp.dream_shops.model.Category;
import com.freeCodeCamp.dream_shops.model.Image;
import com.freeCodeCamp.dream_shops.model.Product;
import com.freeCodeCamp.dream_shops.repo.CategoryRepo;
import com.freeCodeCamp.dream_shops.repo.ImageRepo;
import com.freeCodeCamp.dream_shops.repo.ProductRepo;
import com.freeCodeCamp.dream_shops.request.AddProductRequest;
import com.freeCodeCamp.dream_shops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    private final ImageRepo imageRepo;

    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the DB
        // If Yes, set it as the new product category
        // If No, the save it as a new category
        // The set as the new product category.
        if (productExists(request.getName(), request.getBrand())) {
            throw new AlreadyExistsException(request.getBrand() + " " + request.getName() + " " + "already exists, you may update this product instead");
        }
        Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategory().getName())).orElseGet(() -> {
            Category newCategory = new Category(request.getCategory().getName());
            return categoryRepo.save(newCategory);
        });
        request.setCategory(category);
        return productRepo.save(createProduct(request, category));
    }


    private boolean productExists(String name, String brand) {
        return productRepo.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(request.getName(), request.getBrand(), request.getPrice(), request.getInventory(), request.getDescription(), category);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not Found"));
    }

    @Override
    public void deleteProductById(Long id) {

        productRepo.findById(id).ifPresentOrElse(productRepo::delete, () -> {
            throw new ProductNotFoundException("Product not found");
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepo.findById(productId).map(existingProduct -> updateExistingProduct(existingProduct, request)).map(productRepo::save).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepo.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepo.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepo.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepo.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
