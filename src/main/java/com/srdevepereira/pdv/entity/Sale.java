package com.srdevepereira.pdv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sale")
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "sale_date", nullable = false)
    private LocalDate date;

    @ManyToOne //relação entre as entidades
    @JoinColumn(name = "user_id") //nome da coluna na chave extrangeira
    private User user;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY) //Lazy retorna a venda mas não os itens
    private List<ItemSale> items;
}
