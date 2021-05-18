package com.xoco.nuniez.yugabyte.repositories;

import com.xoco.nuniez.yugabyte.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}