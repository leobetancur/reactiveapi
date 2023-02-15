package com.reactive.student.service;

import com.reactive.student.exception.StudentAlreadyExists;
import com.reactive.student.exception.StudentDoestExist;
import com.reactive.student.model.Student;
import com.reactive.student.repository.StudentRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Single<Student> add(Student student){
        Single<Student> studentExist = Single.error(new StudentAlreadyExists());
        return studentRepository.findByName(student.getName())
                .switchIfEmpty(studentRepository.save(student));
    }

    public Observable<Student> getAll(){
        return studentRepository.findAll()
                .toObservable()
                .filter(student -> student.getActive());
    }

    public Single<Student> getStudentById(Integer studentId){
        return studentRepository.findById(studentId)
                .switchIfEmpty(Single.error(new StudentDoestExist()));
    }

    public Completable deleteStudentById(Integer studentId){
        return studentRepository.deleteById(studentId);
    }

    public Completable updateStudent(Student student){
        return studentRepository.save(student).
                flatMapCompletable(new Function<Student, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Student student) throws Throwable {
                        return Completable.complete();
                    }
                });
    }

}
