package com.reactive.student.service;

import com.reactive.student.exception.StudentDoestExist;
import com.reactive.student.model.Student;
import com.reactive.student.repository.StudentRepository;
import com.reactive.student.web.controller.NewInfoStudent;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          StudentMapper studentMapper){
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Single<Student> add(NewInfoStudent newInfoStudent){
        return studentRepository
                .findByName(newInfoStudent.getName())
                .switchIfEmpty(studentRepository.save(studentMapper.newInfoStudentToStudent(newInfoStudent)));
    }

    public Observable<Student> getAll(){
        return studentRepository.findAll()
                .toObservable()
                .filter(Student::getActive);
    }

    public Single<Student> getStudentById(Integer studentId){
        return studentRepository.findById(studentId)
                .switchIfEmpty(Single.error(new StudentDoestExist()));
    }

    public Completable deleteStudentById(Integer studentId){
        return studentRepository.deleteById(studentId);
    }

    public Completable updateStudent(Student student){
        return studentRepository.save(student)
                .flatMapCompletable(student1 -> Completable.complete());

    }

}
