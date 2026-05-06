package com.ravi.orderflow.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @Schema(example = "s26 Ultra", description = "Product name")
    @NotBlank(message = "Product name is required")
    private String name;

    @Schema(example = "Samsung flagship smartphone", description = "Product description")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Schema(example = "1199.99", description = "Product price")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(example = "25", description = "Initial product quantity")
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

}
