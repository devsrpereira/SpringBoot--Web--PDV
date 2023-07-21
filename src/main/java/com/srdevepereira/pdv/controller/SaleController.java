package com.srdevepereira.pdv.controller;

import com.srdevepereira.pdv.dto.SaleDTO;
import com.srdevepereira.pdv.exception.InvalidOperationException;
import com.srdevepereira.pdv.exception.NoItemException;
import com.srdevepereira.pdv.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(saleService.finAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            return new ResponseEntity<>(saleService.getByID(id), HttpStatus.OK);
        }
        catch (NoItemException | InvalidOperationException error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SaleDTO saleDTO){
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>("Venda realizada com sucesso " + id, HttpStatus.CREATED);
        }catch (NoItemException | InvalidOperationException error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
