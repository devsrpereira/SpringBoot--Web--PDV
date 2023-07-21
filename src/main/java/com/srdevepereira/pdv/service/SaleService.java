package com.srdevepereira.pdv.service;

import com.srdevepereira.pdv.dto.ProductDTO;
import com.srdevepereira.pdv.dto.ProductInfoDTO;
import com.srdevepereira.pdv.dto.SaleDTO;
import com.srdevepereira.pdv.dto.SaleInfoDTO;
import com.srdevepereira.pdv.entity.ItemSale;
import com.srdevepereira.pdv.entity.Product;
import com.srdevepereira.pdv.entity.Sale;
import com.srdevepereira.pdv.entity.User;
import com.srdevepereira.pdv.exception.InvalidOperationException;
import com.srdevepereira.pdv.exception.NoItemException;
import com.srdevepereira.pdv.repository.ItemSaleRepository;
import com.srdevepereira.pdv.repository.ProductRepository;
import com.srdevepereira.pdv.repository.SaleRepository;
import com.srdevepereira.pdv.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;;

    /*
    {
        "user": "Fulano"
        "data": "19/07/2023"
        "products": [
            {
                "description": "Monitor Dell"
                "quantity": 1
                "subtotal": 499.90
            }
        ]
    }

    */

    public List<SaleInfoDTO> finAll(){
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }

    private SaleInfoDTO getSaleInfo(Sale sale) {
        SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
        saleInfoDTO.setUser(sale.getUser().getName());
        saleInfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        saleInfoDTO.setProducts(getProductInfo(sale.getItems()));

        return saleInfoDTO;
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {
        return items.stream().map(item -> {
            ProductInfoDTO productInfoDTO = new ProductInfoDTO();
            productInfoDTO.setDescription(item.getProduct().getDescription());
            productInfoDTO.setQuantity(item.getQuantity());

            return productInfoDTO;

        }).collect(Collectors.toList());
    }


    @Transactional
    public Long save(SaleDTO sale){
        User user = userRepository.findById(sale.getUserId())
                .orElseThrow(()-> new NoItemException("Usuario não encontrado."));

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

        if(products.isEmpty()){
            throw new NoItemException("Não é possivel registrar uma venda sem produtos.");
        }

        return products.stream().map(item -> {

            Product product = productRepository.getReferenceById(item.getProductId());

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if(product.getQuantity() == 0){
                throw new NoItemException(String.format("O produto (%s) está indisponivel no momento.",product.getDescription()));
            } else if (product.getQuantity() < item.getQuantity()) {
                throw new InvalidOperationException(String.format(
                        "Disponivel  em estoque apenas (%s) unid. do produto (%s)," +
                                "favor revisar o pedido.",
                        product.getQuantity(),
                        product.getDescription()));
            }

            int total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());
    }

    public SaleInfoDTO getByID(Long id){
        Sale sale = saleRepository.findById(id)
                .orElseThrow(()-> new NoItemException("Venda não encontrada."));
        return getSaleInfo(sale);
    }


}
