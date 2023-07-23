package com.srdevepereira.pdv.controller;

import com.srdevepereira.pdv.dto.ResponseDTO;
import com.srdevepereira.pdv.entity.User;
import com.srdevepereira.pdv.exception.NoItemException;
import com.srdevepereira.pdv.repository.UserRepository;
import com.srdevepereira.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(@Autowired UserService userService){
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity getAll() {return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);}

    @PostMapping() // criar novo usuario
    public ResponseEntity post(@RequestBody User user){
        try{
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
        }
        catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping() //atualiza usuarios já cadastrados
    public ResponseEntity put(@RequestBody User user){
        try {
            return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
        }
        catch (NoItemException error){
            return new ResponseEntity<>(new ResponseDTO<>(error.getMessage(), user), HttpStatus.BAD_REQUEST);
        }
        catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO<>(error.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try{
            userService.deleteById(id);
            return new ResponseEntity<>("Usuario removido com sucesso", HttpStatus.OK);
        }
        catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
