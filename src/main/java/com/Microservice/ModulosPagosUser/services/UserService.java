package com.Microservice.ModulosPagosUser.services;

import com.Microservice.ModulosPagosUser.dtos.UserDTO;
import com.Microservice.ModulosPagosUser.entities.Role;
import com.Microservice.ModulosPagosUser.entities.User;
import com.Microservice.ModulosPagosUser.models.Account;
import com.Microservice.ModulosPagosUser.repositories.RoleRepository;
import com.Microservice.ModulosPagosUser.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service ("userServiceRestTemplate")
public class UserService implements InterfaceUserService{
    @Autowired
    private RestTemplate clientRest;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User generateUser(UserDTO userDTO){
        User aux = new User(userDTO);
        aux.setRoles(List.of(getRole(1L)));
        logger.info("MSUsers RestTemplated: Role to User added and encode password");
        userRepository.save(aux);
        User user = userRepository.findByDni(aux.getDni());
        clientRest.postForEntity("http://localhost:8001/api/account/createdAccount/", user,Account.class);
        //userRepository.save(user);
        logger.info("MSUsers RestTemplated: created User");
        return user;
    }

    public User updateDataUser(UserDTO userDTO, Long id){
        Optional<User> userToUpdate = userRepository.findById(id);
        if(userToUpdate.isPresent()){
            userToUpdate.get().setDni(userDTO.getDni());
            userToUpdate.get().setMail(userDTO.getMail());
            userToUpdate.get().setName(userDTO.getName());
            userToUpdate.get().setLastName(userDTO.getLastName());
            userToUpdate.get().setPassword(userDTO.getPassword());
            logger.info("MSUsers RestTemplated: Update User");
            return userRepository.save(userToUpdate.get());
        } else {
            return null;
        }

    }

    @Transactional(readOnly = true) //spring
    public List<User> findAll(){
        logger.info("MSUsers RestTemplated: find list all User");
        return (List<User>) userRepository.findAll();
    }
    @Transactional(readOnly = true)
    public User findById(Long id){
        logger.info("MSUsers RestTemplated: find User from idUser");
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByMail(String mail) {
        logger.info("MSUsers RestTemplated: find user from mail");
        return userRepository.findByMail(mail);
    }
    @Transactional(readOnly = true)
    public User findByDni(String dni){
        logger.info("MSUsers RestTemplated: find user from dni");
        return userRepository.findByDni(dni);}
    @Transactional(readOnly = true)
    public Role getRole(Long id){
        return roleRepository.findById(1L).orElse(null);
    }
    public Boolean deleteUserById(Long idUser){
        logger.info("MSUsers RestTemplated: Try deleted User");
        Optional<User> userToDelete = userRepository.findById(idUser);
        if(userToDelete.isPresent()){
            Map <String,String> pathVariables = new HashMap<String,String>();
            pathVariables.put("idUser",idUser.toString());
            List<Account> accountListExist = Arrays.stream(clientRest.getForObject("http://localhost:8001/api/account/listAccount/{idUser}",Account[].class,pathVariables)).toList();
            if(accountListExist.isEmpty()){
                userRepository.delete(userToDelete.get());
                return true;
            }
        }
        return false;
    }

    public List<Account> listUserAccount(Long idUser) {
        logger.info("MSUsers RestTemplated: show if User have accounts");
        Optional<User> userLookinFor = userRepository.findById(idUser);
        if (userLookinFor.isPresent()) {
            Map<String, String> pathVariables = new HashMap<String, String>();
            pathVariables.put("idUser", idUser.toString());
            List<Account> accountListExist = Arrays.stream(clientRest.getForObject("http://localhost:8001/api/account/listAccount/{idUser}", Account[].class, pathVariables)).toList();
            if (!accountListExist.isEmpty()) {
                return accountListExist;// list complete
            }else{
                return accountListExist;//list empty
            }
        }else{
            return null;
        }
    }
}




/*
Map <String,String> pathVariables = new HashMap<String,String>();
        pathVariables.put("id",idAccount.toString());
        Account account = clientRest.getForObject("http://localhost:8001/detail/{id}",Account.class,pathVariables);

        Map <String,String> pathVariables = new HashMap<String,String>();
        pathVariables.put("idUser",user.getId().toString());
        Set<Account> accountList = Arrays.stream(clientRest.getForObject("http://localhost:8001/api/account/listAccount/{idUser}",Account[].class,pathVariables)).collect(Collectors.toSet());
 */