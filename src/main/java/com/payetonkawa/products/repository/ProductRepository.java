package com.payetonkawa.products.repository;

import com.payetonkawa.products.entity.ProductsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductsEntity")
    void clear();
}
