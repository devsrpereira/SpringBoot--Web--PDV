package com.srdevepereira.pdv.service;

import com.srdevepereira.pdv.dto.ProductDTO;
import com.srdevepereira.pdv.dto.SaleDTO;
import com.srdevepereira.pdv.entity.ItemSale;
import com.srdevepereira.pdv.entity.Product;
import com.srdevepereira.pdv.entity.Sale;
import com.srdevepereira.pdv.entity.User;
import com.srdevepereira.pdv.repository.ItemSaleRepository;
import com.srdevepereira.pdv.repository.ProductRepository;
import com.srdevepereira.pdv.repository.SaleRepository;
import com.srdevepereira.pdv.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;;


    @Transactional
    public Long save(SaleDTO sale){
        User user = userRepository.findById(sale.getUserId()).get();

        Sale newSale = new Sale();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());
        List<ItemSale> items = getItemSale(sale.getItems());

        newSale = saleRepository.save(newSale);

        saveItemSale(items, newSale);

        return newSale.getId();
    }

    private void saveItemSale(List<ItemSale> items, Sale newSale) {
        for(ItemSale item: items){
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }
    }

    private List<ItemSale> getItemSale(List<ProductDTO> products){

        return products.stream().map(item -> {

            Product product = productRepository.getReferenceById(item.getProductId());

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            return itemSale;
        }).collect(Collectors.toList());
    }
}
