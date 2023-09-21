package com.korkmazyusufcan.questionapp.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message, ExceptionEntity exceptionEntity){
        super(exceptionEntity.name() + " already exist!!! " + message );
    }

    public AlreadyExistException(ExceptionEntity exceptionEntity){
        super(exceptionEntity.name() + " already exist!!!");
    }
}
