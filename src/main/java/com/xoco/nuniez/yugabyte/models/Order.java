package com.xoco.nuniez.yugabyte.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private Long userId;

    @Column(columnDefinition = "numeric(10,2)")
    private Double orderTotal;

    @Transient
    @ElementCollection
    private Set<Product> products = new HashSet<>();

    @Getter
    public static class Product {
        private Long productId;
        private Integer units;
    }

}