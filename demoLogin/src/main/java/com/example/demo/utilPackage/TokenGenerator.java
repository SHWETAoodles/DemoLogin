package com.example.demo.utilPackage;

import java.util.UUID;

public class TokenGenerator {
    private TokenGenerator(){

    }
    public static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
