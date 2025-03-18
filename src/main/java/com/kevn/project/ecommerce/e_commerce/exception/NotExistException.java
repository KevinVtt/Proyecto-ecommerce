package com.kevn.project.ecommerce.e_commerce.exception;

public class NotExistException extends RuntimeException {
    
    public NotExistException(String message){
        super(message);
    }
}
