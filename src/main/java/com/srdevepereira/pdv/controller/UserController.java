package com.srdevepereira.pdv.controller;

import com.srdevepereira.pdv.entity.User;
import com.srdevepereira.pdv.repository.UserRepository;
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

    private UserRepository userRepository;

    public UserController(@Autowired UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity getAll() {
        ResponseEntity<List<User>> entity = new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        return entity;
    }

    @PostMapping() // criar novo usuario
    public ResponseEntity post(@RequestBody User user){
        try{
            user.setEnabled(true);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        }
        catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping() //atualiza usuarios já cadastrados
    public ResponseEntity put(@RequestBody User user){
        Optional<User> userToEdit = userRepository.findById(user.getId());

        if(userToEdit.isPresent()){
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build(); //usamos quando queremos definir um padrão de tratativa de erro vazia
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try{
            userRepository.deleteById(id);
            return new ResponseEntity<>("Usuario removido com sucesso", HttpStatus.OK);
        }
        catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
