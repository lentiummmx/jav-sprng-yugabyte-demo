package com.xoco.nuniez.yugabyte.repositories;

import com.xoco.nuniez.yugabyte.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}