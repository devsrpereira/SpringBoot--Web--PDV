package com.srdevepereira.pdv.repository;

import com.srdevepereira.pdv.entity.ItemSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSaleRepository extends JpaRepository<ItemSale, Long> {
}
