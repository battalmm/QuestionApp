package com.korkmazyusufcan.questionapp.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message, ExceptionEntity exceptionEntity){
        super(exceptionEntity.name() + " not found! " + message);
    }

    public NotFoundException(ExceptionEntity exceptionEntity){
        super(exceptionEntity.name() + " Not found ");
    }
}
