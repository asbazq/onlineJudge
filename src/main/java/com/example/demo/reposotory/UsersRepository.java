package com.example.demo.reposotory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Users;


public interface UsersRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByUsername(String username);    
    List<Users> findByEmail(String email);
    Optional<Users> findById(Long id);
}
