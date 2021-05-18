package com.xoco.nuniez.yugabyte.repositories;

import com.xoco.nuniez.yugabyte.models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {
    public List<OrderLine> findByOrderId(UUID orderId);
}