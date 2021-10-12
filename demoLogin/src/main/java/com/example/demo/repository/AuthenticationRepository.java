package com.example.demo.repository;

import com.example.demo.model.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findByToken(String token);
    AuthenticationToken findByTokenAndIsDeleted(String token,boolean isDeleted);
}
