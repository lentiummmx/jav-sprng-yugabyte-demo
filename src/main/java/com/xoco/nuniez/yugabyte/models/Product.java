package com.xoco.nuniez.yugabyte.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long productId;

    @Size(max = 50)
    private String productName;

    private String description;

    @Column(columnDefinition = "numeric(10,2)")
    private Double price;

}