package com.reactive.student.exception;

public class StudentAlreadyExists extends RuntimeException{


    public static final String STUDENT_ALREADY_EXISTS = "Student already Exists";

    public StudentAlreadyExists(){
        super(STUDENT_ALREADY_EXISTS);
    }
}
