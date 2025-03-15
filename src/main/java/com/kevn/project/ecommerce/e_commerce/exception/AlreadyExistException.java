package com.kevn.project.ecommerce.e_commerce.exception;

public class AlreadyExistException extends RuntimeException {
   public AlreadyExistException(String message){
        super(message);
   }

   public AlreadyExistException(){
    }
}
