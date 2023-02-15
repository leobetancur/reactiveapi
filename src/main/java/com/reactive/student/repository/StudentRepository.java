package com.reactive.student.repository;

import com.reactive.student.model.Student;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.RxJava2CrudRepository;

public interface StudentRepository extends RxJava2CrudRepository<Student, Integer> {

    @Query("SELECT * FROM student WHERE name = :name")
    Maybe<Student> findByName(String name);
}
