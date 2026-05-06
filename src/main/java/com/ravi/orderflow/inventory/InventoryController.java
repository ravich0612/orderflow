package com.ravi.orderflow.inventory;

import com.ravi.orderflow.inventory.dto.InventoryRequest;
import com.ravi.orderflow.inventory.dto.InventoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "Inventory and stock management APIs")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(
            summary = "Create inventory for product",
            description = "Creates one inventory record for a product. Each product can have only one inventory record."
    )
    @PostMapping("/products/{productId}/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(
            @PathVariable UUID productId,
            @Valid @RequestBody InventoryRequest request
    ) {
        return inventoryService.createInventory(productId, request);
    }

    @Operation(
            summary = "Get inventory by product ID",
            description = "Returns inventory details for a specific product UUID."
    )
    @GetMapping("/products/{productId}/inventory")
    public InventoryResponse getInventoryByProductId(@PathVariable UUID productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @Operation(
            summary = "Update inventory for product",
            description = "Updates inventory details for a specific product UUID."
    )
    @PutMapping("/products/{productId}/inventory")
    public InventoryResponse updateInventory(
            @PathVariable UUID productId,
            @Valid @RequestBody InventoryRequest request
    ) {
        return inventoryService.updateInventory(productId, request);
    }

    @Operation(
            summary = "Get all inventories",
            description = "Returns all inventory records."
    )
    @GetMapping("/inventories")
    public List<InventoryResponse> getAllInventories() {
        return inventoryService.getAllInventories();
    }
}