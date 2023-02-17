package com.reactive.student.domain.repository;

import com.reactive.student.domain.entities.Student;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

public interface StudentCache {

    void save(Student student);

    Maybe<Student> findById(Integer id);

    Completable deleteById(Integer id);
}
