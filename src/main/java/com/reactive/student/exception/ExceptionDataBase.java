package com.reactive.student.exception;

public class ExceptionDataBase extends RuntimeException{
    public ExceptionDataBase(String codeError, Throwable throwable){
        super("Code Error "+codeError+ ". DataBase Error");
    }
}
