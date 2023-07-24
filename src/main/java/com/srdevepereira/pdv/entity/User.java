package com.srdevepereira.pdv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório")
    private String name;

    private boolean isEnabled;

    @OneToMany(mappedBy = "user") //atenção com o nome que foi utilizado na relação entre as entidades, eles devem ser o mesmos
    private List<Sale> sales;
}
