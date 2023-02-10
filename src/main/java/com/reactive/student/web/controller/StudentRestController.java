package com.reactive.student.web.controller;

import com.reactive.student.model.Student;
import com.reactive.student.service.StudentService;
import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    private StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public Single<ResponseEntity<Student>> addStudent(@RequestBody Student student){
        return studentService.add(student)
                .subscribeOn(Schedulers.io())
                .map(studentBd-> ResponseEntity
                        .created(URI.create("/students/"+studentBd.getId()))
                        .body(studentBd));
    }

/*    @GetMapping
    public Observable<ResponseEntity<Student>> getAllStudents(){
        return studentService.getAll()
                .subscribeOn(Schedulers.io())
                .map(allStudents-> ResponseEntity.ok(allStudents));
    }*/

    @GetMapping
    public ResponseEntity<Observable<Student>> getAllStudents(){
        Observable<Student> allStudents = studentService.getAll();
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping(value="/{studentId}")
    public Single<ResponseEntity<Student>> getStudentById(@PathVariable Integer studentId){
        return studentService.getStudentById(studentId)
                .subscribeOn(Schedulers.io())
                .map(student -> ResponseEntity.ok(student));
    }

    @DeleteMapping(value="/{studentId}")
    public Completable deleteStudentById(@PathVariable Integer studentId){
        return studentService.deleteStudentById(studentId)
                .subscribeOn(Schedulers.io());
    }

    @PutMapping
    public Completable updateStudent(@RequestBody Student student ){
        return studentService.updateStudent(student)
                .subscribeOn(Schedulers.io());
    }
}
