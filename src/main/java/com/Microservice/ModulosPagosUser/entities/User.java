package com.Microservice.ModulosPagosUser.entities;

import com.Microservice.ModulosPagosUser.dtos.UserDTO;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name="Users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name ="native",strategy = "native")
    private Long id;
    private String name;
    private String lastName;
    @Column(unique = true)
    private String dni;
    @Column(unique = true, length = 20)
    private String mail;
    @Column(length = 60)
    private String password;
    private Boolean isActive;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name="users_roles",joinColumns =@JoinColumn(name="user_id"),
            inverseJoinColumns =@JoinColumn(name="role_id"),
            uniqueConstraints={@UniqueConstraint(columnNames={"user_id","role_id"})})
    private List<Role> roles =new ArrayList<>();


// set and geters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    // contructer
    public User() {
    }

    public User(UserDTO userDTO){
        this.name = userDTO.getName();
        this.lastName = userDTO.getLastName();
        this.dni = userDTO.getDni();
        this.mail = userDTO.getMail();
        String encodePass = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        this.password = encodePass;
        this.isActive = true;
    }
}
