package com.ravi.orderflow.inventory;

import com.ravi.orderflow.exception.InventoryAlreadyExistsException;
import com.ravi.orderflow.exception.InventoryNotFoundException;
import com.ravi.orderflow.exception.ProductNotFoundException;
import com.ravi.orderflow.inventory.dto.InventoryRequest;
import com.ravi.orderflow.inventory.dto.InventoryResponse;
import com.ravi.orderflow.product.Product;
import com.ravi.orderflow.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryResponse createInventory(UUID productId, InventoryRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        if (inventoryRepository.existsByProductId(productId)) {
            throw new InventoryAlreadyExistsException("Inventory already exists for product id: " + productId);
        }

        Inventory inventory = Inventory.builder()
                .product(product)
                .quantity(request.getQuantity())
                .reservedQuantity(request.getReservedQuantity())
                .reorderLevel(request.getReorderLevel())
                .build();

        Inventory savedInventory = inventoryRepository.save(inventory);

        return mapToResponse(savedInventory);
    }

    public InventoryResponse getInventoryByProductId(UUID productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product id: " + productId));

        return mapToResponse(inventory);
    }

    public InventoryResponse updateInventory(UUID productId, InventoryRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product id: " + productId));

        inventory.setQuantity(request.getQuantity());

        if (request.getReservedQuantity() != null) {
            inventory.setReservedQuantity(request.getReservedQuantity());
        }

        if (request.getReorderLevel() != null) {
            inventory.setReorderLevel(request.getReorderLevel());
        }

        Inventory updatedInventory = inventoryRepository.save(inventory);

        return mapToResponse(updatedInventory);
    }

    public List<InventoryResponse> getAllInventories() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        Integer quantity = inventory.getQuantity();
        Integer reservedQuantity = inventory.getReservedQuantity();

        Integer availableQuantity = quantity - reservedQuantity;
        Boolean lowStock = availableQuantity <= inventory.getReorderLevel();

        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProduct().getId())
                .productName(inventory.getProduct().getName())
                .quantity(inventory.getQuantity())
                .reservedQuantity(inventory.getReservedQuantity())
                .availableQuantity(availableQuantity)
                .reorderLevel(inventory.getReorderLevel())
                .lowStock(lowStock)
                .createdAt(inventory.getCreatedAt())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }
}
