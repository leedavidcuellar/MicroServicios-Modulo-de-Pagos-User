package com.Microservice.ModulosPagosUser.repositories;

import com.Microservice.ModulosPagosUser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Long> {
    User findByDni(String dni);

    User findByMail(String mail);
}
