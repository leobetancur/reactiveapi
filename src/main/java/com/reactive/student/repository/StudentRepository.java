package com.reactive.student.repository;

import com.reactive.student.model.Student;

import io.reactivex.rxjava3.core.Maybe;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.RxJava3CrudRepository;


public interface StudentRepository extends RxJava3CrudRepository<Student, Integer> {

    @Query("SELECT * FROM student WHERE name = :name")
    Maybe<Student> findByName(String name);
}
