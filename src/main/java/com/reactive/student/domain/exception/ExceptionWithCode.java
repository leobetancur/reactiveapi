package com.reactive.student.domain.exception;

public class ExceptionWithCode extends RuntimeException{

    private final String code;

    public ExceptionWithCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
