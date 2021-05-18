package com.xoco.nuniez.yugabyte.controllers;

import com.xoco.nuniez.yugabyte.exceptions.ResourceNotFoundException;
import com.xoco.nuniez.yugabyte.models.Order;
import com.xoco.nuniez.yugabyte.models.OrderLine;
import com.xoco.nuniez.yugabyte.repositories.OrderLineRepository;
import com.xoco.nuniez.yugabyte.repositories.OrderRepository;
import com.xoco.nuniez.yugabyte.repositories.ProductRepository;
import com.xoco.nuniez.yugabyte.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @GetMapping
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order) {
        double orderTotal = 0.0;
        for (Order.Product orderProduct: order.getProducts()) {
            orderTotal += productRepository.findById(orderProduct.getProductId())
                    .map(p -> { return p.getPrice() * orderProduct.getUnits(); })
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + orderProduct.getProductId()));
        }

        order.setOrderTotal(orderTotal);
        order.setUser(userRepository.findById(order.getUserId())
                .map(user -> { return user; })
                .orElseThrow(() -> new ResourceNotFoundException("UserId " + order.getUserId() + " not found")));

        Order createdOrder = orderRepository.save(order);
        for (Order.Product orderProduct : order.getProducts()) {
            orderLineRepository.save(new OrderLine(createdOrder.getOrderId(), orderProduct.getProductId(), orderProduct.getUnits()));
        }

        return createdOrder;
    }

    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable UUID orderId, @Valid @RequestBody Order orderRequest) {
        for (OrderLine orderLine : orderLineRepository.findByOrderId(orderId)) {
            orderLineRepository.delete(orderLine);
        }

        for (Order.Product orderProduct : orderRequest.getProducts()) {
            orderLineRepository.save(new OrderLine(orderId, orderProduct.getProductId(), orderProduct.getUnits()));
        }

        Order updatedOrder = orderRepository.findById(orderId)
                .map(o -> {
                    double orderTotal = 0.0;
                    for (Order.Product orderProduct : orderRequest.getProducts()) {
                        orderTotal += productRepository.findById(orderProduct.getProductId())
                                .map(p -> { return p.getPrice() * orderProduct.getUnits(); })
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found with Id: " + orderProduct.getProductId()));
                    }

                    o.setUser(userRepository.findById(orderRequest.getUserId())
                            .map(user -> { return user; })
                            .orElseThrow(() -> new ResourceNotFoundException("UserId " + o.getUserId() + " not found")));

                    o.setOrderTotal(orderTotal);
                    return orderRepository.save(o);
                })
                .orElseThrow(() ->new ResourceNotFoundException("Order not found with id " + orderId));

        return updatedOrder;
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID orderId) {
        return orderRepository.findById(orderId)
                .map(o -> {
                    orderRepository.delete(o);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }

}
