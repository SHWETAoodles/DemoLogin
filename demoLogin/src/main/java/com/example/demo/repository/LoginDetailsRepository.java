package com.example.demo.repository;

import com.example.demo.dto.LoginDetails;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LoginDetailsRepository extends JpaRepository<LoginDetails,Long> {
    LoginDetails findByUser(User user);
    List<LoginDetails> findByUserAndIsDeleted(User user, boolean isDeleted);
}
