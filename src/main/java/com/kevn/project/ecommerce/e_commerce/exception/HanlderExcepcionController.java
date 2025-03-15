package com.kevn.project.ecommerce.e_commerce.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HanlderExcepcionController {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> NotFound(NotFoundException e){

        Map<String,Object> error = new HashMap<>();
        error.put("Date", new Date().toString());
        error.put("Message", e.getMessage());
        error.put("Error", "No se encontro el objeto con ese id");
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
        error.put("Error", "El objeto que estas intentando mandar es nulo");
        error.put("Cause", e.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
