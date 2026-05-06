package com.ravi.orderflow.product;

import com.ravi.orderflow.product.dto.ProductRequest;
import com.ravi.orderflow.product.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create product",
            description = "Creates a new product with name, description, price, and quantity."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @Operation(
            summary = "Get all products",
            description = "Returns all active products. Deactivated products are excluded."
    )
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(
            summary = "Get product by ID",
            description = "Returns a single active product by UUID."
    )
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable UUID id) {

        return productService.getProductById(id);
    }

    @Operation(
            summary = "Update product",
            description = "Updates product details for the provided UUID."
    )
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @Operation(
            summary = "Deactivate product",
            description = "Soft deletes a product by setting active=false."
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProductResponse deactivateProduct(@PathVariable UUID id) {
        return productService.deactivateProduct(id);
    }

}
