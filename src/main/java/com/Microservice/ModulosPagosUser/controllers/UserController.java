package com.Microservice.ModulosPagosUser.controllers;

import com.Microservice.ModulosPagosUser.services.UserService;
import com.Microservice.ModulosPagosUser.dtos.UserDTO;
import com.Microservice.ModulosPagosUser.entities.User;
import com.Microservice.ModulosPagosUser.models.Account;
import com.Microservice.ModulosPagosUser.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RefreshScope
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${configuration.text}")
    private String text;

    @Autowired
    private Environment env;
    @PostMapping("/generatedUser")
    public ResponseEntity<Object> generatedUser(@RequestBody UserDTO userDTO) {
        try {
            int a = userDTO.getDni().length();
            if (!(a > 6 && a < 9)) {
                return new ResponseEntity<>("DNI field must have 7 or 8 digits", HttpStatus.NOT_ACCEPTABLE);
            }
            if (!Utils.verifyNumber(userDTO.getDni())) {
                return new ResponseEntity<>("Error in DNI field, please check it only numbers.", HttpStatus.NOT_ACCEPTABLE);
            }
            if (userDTO.getName().trim().isEmpty() || userDTO.getLastName().trim().isEmpty() || userDTO.getDni().trim().isEmpty()
                    || userDTO.getMail().trim().isEmpty() || userDTO.getPassword().trim().isEmpty()) {
                return new ResponseEntity<>("Missing data, please check all fields", HttpStatus.NOT_ACCEPTABLE);
            }
            if (userService.findByDni(userDTO.getDni()) == null) {
                User userNew = userService.generateUser(userDTO);
                return new ResponseEntity<>(userNew, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("User ready exit, try other data", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateDataUser")
    public ResponseEntity<Object> updateDataUser(@RequestBody UserDTO userDTO, @RequestParam Long id) {
        try {
            int a = userDTO.getDni().length();
            if (!(a > 6 && a < 9)) {
                return new ResponseEntity<>("DNI field must have 7 or 8 digits", HttpStatus.NOT_ACCEPTABLE);
            }
            if (!Utils.verifyNumber(userDTO.getDni())) {
                return new ResponseEntity<>("Error in DNI field, please check it only numbers.", HttpStatus.NOT_ACCEPTABLE);
            }
            if (userDTO.getName().trim().isEmpty() || userDTO.getLastName().trim().isEmpty() || userDTO.getDni().trim().isEmpty()
                    || userDTO.getMail().trim().isEmpty() || userDTO.getPassword().trim().isEmpty()) {
                return new ResponseEntity<>("Missing data, please check all fields", HttpStatus.NOT_ACCEPTABLE);
            }

            if (userService.findByDni(userDTO.getDni()) != null) {
                User userToUpdate = userService.updateDataUser(userDTO, id);
                return new ResponseEntity<>(userToUpdate, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User NO exits, try other data", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{idUser}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long idUser) {
        if (userService.deleteUserById(idUser)) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User NO deleted because no exist or have account", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/list")//spring
    public List<User> getListUser() {
        return userService.findAll();
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> detailUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if(user==null){
            return new ResponseEntity<>("User NO exits, check information", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/detail2/{id}")
    public ResponseEntity<Object> detail2User(@PathVariable Long id) {
        User user = userService.findById(id);
        if(user==null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/findByMail/{mail}")
    public ResponseEntity<Object> findUserByMail(@PathVariable String mail) {
        User user = userService.findByMail(mail);
        if(user==null){
            return new ResponseEntity<>("User NO exits, check information", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/listUserAccount/{idUser}")
    public ResponseEntity<Object> listUserAccount(@PathVariable Long idUser) {
        List<Account> listUserAccount = userService.listUserAccount(idUser);
        if (listUserAccount==null) {
            return new ResponseEntity<>("User NO exits, check information", HttpStatus.NOT_ACCEPTABLE);
        }
        if (listUserAccount.isEmpty()) {
            return new ResponseEntity<>("User NO have accounts", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(listUserAccount, HttpStatus.OK);
    }

    @GetMapping("/getConfig")
    public ResponseEntity<?> getConfig(@Value("${server.port}") String port){
        Map<String,String> json = new HashMap<>();
        json.put("text",this.text);
        json.put("port",port);
        if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor.name", env.getProperty("configuration.autor.name"));
            json.put("autor.email", env.getProperty("configuration.autor.email"));
        }
        return new ResponseEntity<Map<String,String>>(json,HttpStatus.OK);
    }

}

/*
    private ResponseEntity<Object>checkingData(UserDTO userDTO) {
        int a = userDTO.getDni().length();
        if (!(a > 6 && a < 9)) {
            return new ResponseEntity<>("DNI field must have 7 or 8 digits",HttpStatus.NOT_ACCEPTABLE);
        }
        if (!Utils.verifyNumber(userDTO.getDni())) {
            return new ResponseEntity<>("Error in DNI field, please check it only numbers.",HttpStatus.NOT_ACCEPTABLE);
        }
        if (userDTO.getName().isEmpty() || userDTO.getLastName().isEmpty() || userDTO.getDni().isEmpty()
                || userDTO.getMail().isEmpty() || userDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing data, please check all fields",HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

 */
