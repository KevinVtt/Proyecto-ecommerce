package com.kevn.project.ecommerce.e_commerce.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kevn.project.ecommerce.e_commerce.exception.AlreadyExistException;
import com.kevn.project.ecommerce.e_commerce.exception.NotExistException;
import com.kevn.project.ecommerce.e_commerce.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> NotFound(NotFoundException e){
        Map<String,Object> error = new HashMap<>();
        error.put("Date", new Date().toString());
        error.put("Message", e.getMessage());
        error.put("Error", "No se encontro el objeto");
        error.put("Cause", e.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({AlreadyExistException.class})
    public ResponseEntity<?> AlreadyExist(AlreadyExistException e){
        Map<String,Object> error = new HashMap<>();
        error.put("Date", new Date().toString());
        error.put("Message", e.getMessage());
        error.put("Error", "El objeto ya se encuentra en la base de datos");
        error.put("Cause", e.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<?> NullPonter(NullPointerException e){
        Map<String,Object> error = new HashMap<>();
        error.put("Date", new Date().toString());
        error.put("Message", e.getMessage());
        error.put("Error", "El objeto no puede ser nulo");
        error.put("Cause", e.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({NotExistException.class})
    public ResponseEntity<?> NotExist(NotExistException e){
        Map<String,Object> error = new HashMap<>();
        error.put("Date", new Date().toString());
        error.put("Message", e.getMessage());
        error.put("Error", "No existe ese objeto en la base de datos!");
        error.put("Cause", e.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
