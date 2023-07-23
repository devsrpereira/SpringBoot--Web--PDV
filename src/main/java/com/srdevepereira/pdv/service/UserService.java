package com.srdevepereira.pdv.service;

import com.srdevepereira.pdv.dto.UserDTO;
import com.srdevepereira.pdv.entity.User;
import com.srdevepereira.pdv.exception.NoItemException;
import com.srdevepereira.pdv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll(){
        return userRepository.findAll().stream().map(user ->
                new UserDTO(user.getId(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    public UserDTO save(User user){
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }

    public UserDTO findById(Long id){
        Optional<User> optional = userRepository.findById(id);

        if(!optional.isPresent()){
            throw new NoItemException("Usuario não encontrado.");
        }
        User user = optional.get();
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }

    public UserDTO update(User user){
        Optional<User> userToEdit = userRepository.findById(user.getId());

        if(!userToEdit.isPresent()){
            throw new NoItemException("Usuario não encontrado.");
        }
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }



    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
