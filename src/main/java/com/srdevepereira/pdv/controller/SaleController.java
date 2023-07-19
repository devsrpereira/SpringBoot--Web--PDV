package com.srdevepereira.pdv.controller;

import com.srdevepereira.pdv.dto.SaleDTO;
import com.srdevepereira.pdv.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(saleService.finAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO saleDTO){
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>("Venda realizada com sucesso " + id, HttpStatus.CREATED);
        }
        catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
