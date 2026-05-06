package com.ravi.orderflow.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {

    @Schema(example = "25", description = "Total stock quantity")
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @Schema(example = "0", description = "Quantity reserved for carts or pending orders")
    @Min(value = 0, message = "Reserved quantity cannot be negative")
    private Integer reservedQuantity;

    @Schema(example = "5", description = "Threshold for low-stock detection")
    @Min(value = 0, message = "Reorder level cannot be negative")
    private Integer reorderLevel;

}
