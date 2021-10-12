package com.example.demo.repository;

import com.example.demo.model.Otp;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Long> {
    Otp findByOtp(int otp);
    Otp findByUser(User user);
    Otp findByOtpAndUser(int otp,User user);
}
