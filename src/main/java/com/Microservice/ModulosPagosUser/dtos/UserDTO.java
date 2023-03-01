package com.Microservice.ModulosPagosUser.dtos;

import com.Microservice.ModulosPagosUser.entities.Role;
import com.Microservice.ModulosPagosUser.entities.User;

import java.util.List;

public class UserDTO {

    private String name;
    private String lastName;
    private String dni;
    private String mail;
    private String password;
    private Boolean isActive;

    private List<Role> role;


    // seters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    // constructors
    public UserDTO(User user){
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.dni = user.getDni();
        this.mail = user.getMail();
        this.password = user.getPassword();
        this.isActive=user.getActive();
    }

    public UserDTO() {
    }
}
