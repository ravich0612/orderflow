package com.ravi.orderflow.inventory;

import com.ravi.orderflow.inventory.dto.InventoryRequest;
import com.ravi.orderflow.inventory.dto.InventoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/products/{productId}/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(
            @PathVariable UUID productId,
            @Valid @RequestBody InventoryRequest request
    ) {
        return inventoryService.createInventory(productId, request);
    }

    @GetMapping("/products/{productId}/inventory")
    public InventoryResponse getInventoryByProductId(@PathVariable UUID productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @PutMapping("/products/{productId}/inventory")
    public InventoryResponse updateInventory(
            @PathVariable UUID productId,
            @Valid @RequestBody InventoryRequest request
    ) {
        return inventoryService.updateInventory(productId, request);
    }

    @GetMapping("/inventories")
    public List<InventoryResponse> getAllInventories() {
        return inventoryService.getAllInventories();
    }
}