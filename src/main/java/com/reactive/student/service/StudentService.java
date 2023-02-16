package com.reactive.student.service;

import com.reactive.student.exception.ExceptionDataBase;
import com.reactive.student.exception.StudentAlreadyExists;
import com.reactive.student.exception.StudentDoestExist;
import com.reactive.student.model.Student;
import com.reactive.student.repository.StudentRepository;
import com.reactive.student.web.controller.NewInfoStudent;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          StudentMapper studentMapper){
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Single<Student> add(NewInfoStudent newInfoStudent){
        return Single.just(newInfoStudent)
                .map(infoStudent->studentMapper.newInfoStudentToStudent(newInfoStudent))
                .flatMap(student ->studentRepository
                        .findByName(student.getName())
                        .switchIfEmpty(studentRepository.save(student)))
                        .onErrorResumeNext( t-> Single.error(new ExceptionDataBase("1", t)))
                .doOnError(t->logger.error(t.getMessage()));
    }

    public Observable<Student> getAll(){
        return studentRepository.findAll()
                .filter(Student::getActive)
                .toObservable();
    }

    public Single<Student> getStudentById(Integer studentId){
        return studentRepository.findById(studentId)
                .switchIfEmpty(Single.error(new StudentDoestExist()));
    }

    public Completable deleteStudentById(Integer studentId){
        return studentRepository.existsById(studentId)
                .flatMapCompletable(studentExist->{
                    if(!studentExist) {
                        return Completable.error(new StudentDoestExist());
                    }
                    return studentRepository.deleteById(studentId);
                });
    }

    public Completable updateStudent(Student student){
        return studentRepository.existsById(student.getId())
                .flatMapCompletable(studentExist->{
                    if(!studentExist){
                        return Completable.error(new StudentDoestExist());
                    }
                    return studentRepository.save(student).ignoreElement();
                });
    }

}
