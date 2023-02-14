package com.reactive.student.service;

import com.reactive.student.exception.StudentAlreadyExists;
import com.reactive.student.model.Student;
import com.reactive.student.repository.StudentRepository;
import io.reactivex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Single<Student> add(Student student){
        Mono<Student> monoStudent = Mono.error(new StudentAlreadyExists());
        return RxJava2Adapter.monoToSingle(studentRepository
                .findByName(student.getName())
                .flatMap(existStudent-> monoStudent)
                .switchIfEmpty(studentRepository.save(student)));

    }

    public Observable<Student> getAll(){

        return RxJava2Adapter.fluxToObservable(studentRepository.findAll())
                .filter(student -> student.getActive());
    }

    public Single<Student> getStudentById(Integer studentId){
        return RxJava2Adapter.monoToSingle(studentRepository.findById(studentId));
    }

    public Completable deleteStudentById(Integer studentId){
        return RxJava2Adapter.monoToCompletable(studentRepository.deleteById(studentId));
    }

    public Completable updateStudent(Student student){
        return RxJava2Adapter.monoToCompletable(studentRepository.save(student));
    }

}
