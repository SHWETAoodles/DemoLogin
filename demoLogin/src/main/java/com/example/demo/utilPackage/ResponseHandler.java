package com.example.demo.utilPackage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ResponseHandler {
    private ResponseHandler() {

    }
    public static ResponseEntity<Object> response(HttpStatus httpStatus,Boolean isError,String msg,Object resposneObject){
        Map<String,Object> map=new TreeMap<>();
        map.put("tmestamp",new Date().getTime());
        map.put("status",httpStatus.value());
        map.put("isError",isError);
        map.put("message",msg);
        map.put("response Object",resposneObject);
        return new ResponseEntity<>(map,httpStatus);

    }
    public static ResponseEntity<Object> response(HttpStatus httpStatus,boolean isError,String message){
        Map<String,Object> map=new TreeMap<>();
        map.put("tmestamp",new Date().getTime());
        map.put("status",httpStatus.value());
        map.put("isError",isError);
        map.put("message",message);
        return new ResponseEntity<>(map,httpStatus);
    }

}
