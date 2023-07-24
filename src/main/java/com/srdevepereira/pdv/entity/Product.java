package com.srdevepereira.pdv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "O campo descrição é obrigatório")
    private String description;

    @Column(length = 20, precision = 20, scale = 2, nullable = false) // precision é para números antes da virgula e o scale é para depois da virgula
    @NotNull(message = "O campo preço é obrigatório")
    private BigDecimal price;

    @Column(nullable = true)
    @NotNull(message = "O campo quantidade é obrigatório")
    private int quantity;
}
