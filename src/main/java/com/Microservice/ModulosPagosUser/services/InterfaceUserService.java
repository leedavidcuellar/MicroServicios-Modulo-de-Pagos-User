package com.Microservice.ModulosPagosUser.services;

import com.Microservice.ModulosPagosUser.dtos.UserDTO;
import com.Microservice.ModulosPagosUser.entities.User;

import java.util.List;


public interface InterfaceUserService {

    public User generateUser(UserDTO userDTO);
    public List<User> findAll();

    public User findById(Long id);

    public User findByMail(String mail);


}
