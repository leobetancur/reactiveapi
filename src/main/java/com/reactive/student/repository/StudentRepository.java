package com.reactive.student.repository;

import com.reactive.student.model.Student;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {

    @Query("SELECT * FROM student WHERE name = :name")
    Mono<Student> findByName(String name);
}
