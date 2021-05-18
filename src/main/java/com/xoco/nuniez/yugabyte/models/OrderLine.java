package com.xoco.nuniez.yugabyte.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "order_line")
@IdClass(OrderLine.IdClass.class)
public class OrderLine {
    @Id
    private UUID orderId;

    @Id
    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @Id
    private Long productId;

    @Id
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
    private Product product;

    private Integer units;

    public OrderLine() {
    }

    public OrderLine(UUID orderId, Long productId, Integer units) {
        this.orderId = orderId;
        this.productId = productId;
        this.units = units;
    }

    public static class IdClass implements Serializable {
        private UUID orderId;
        private Long productId;
    }
}