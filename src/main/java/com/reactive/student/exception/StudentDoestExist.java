package com.reactive.student.exception;

public class StudentDoestExist extends RuntimeException{
    public static final String STUDENT_DOESNT_EXISTS = "Student doesn't Exists";

    public StudentDoestExist(){
        super(STUDENT_DOESNT_EXISTS);
    }
}
