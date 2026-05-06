package com.ravi.orderflow.product;

import com.ravi.orderflow.exception.ProductAlreadyExistsException;
import com.ravi.orderflow.exception.ProductNotFoundException;
import com.ravi.orderflow.product.dto.ProductRequest;
import com.ravi.orderflow.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByNameIgnoreCaseAndPriceAndDescriptionIgnoreCase(
                request.getName(),
                request.getPrice(),
                request.getDescription()
        )) {
            throw new ProductAlreadyExistsException(
                    "Product already exists with same name, price, and description."
            );
        }

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .active(true)
                .build();

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .filter(Product::getActive)
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return mapToResponse(product);
    }

    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (productRepository.existsByNameIgnoreCaseAndPriceAndDescriptionIgnoreCaseAndIdNot(
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                id
        )) {
            throw new ProductAlreadyExistsException(
                    "Another product already exists with same name, price, and description."
            );
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    public ProductResponse deactivateProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        product.setActive(false);
        productRepository.save(product);
        return mapToResponse(product);
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .active(product.getActive())
                .createdAt(String.valueOf(product.getCreatedAt()))
                .updatedAt(String.valueOf(product.getUpdatedAt()))
                .build();
    }


}
