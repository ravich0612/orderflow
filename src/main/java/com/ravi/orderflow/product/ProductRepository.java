package com.ravi.orderflow.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByNameIgnoreCaseAndPriceAndDescriptionIgnoreCase(
            String name,
            BigDecimal price,
            String description
    );

    boolean existsByNameIgnoreCaseAndPriceAndDescriptionIgnoreCaseAndIdNot(
            String name,
            BigDecimal price,
            String description,
            UUID id
    );
}
