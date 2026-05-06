package com.ravi.orderflow.inventory.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {

    private UUID id;
    private UUID productId;
    private String productName;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;
    private Integer reorderLevel;
    private Boolean lowStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
