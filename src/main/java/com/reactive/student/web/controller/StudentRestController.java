package com.reactive.student.web.controller;

import com.reactive.student.model.Student;
import com.reactive.student.service.StudentService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/students")
public class StudentRestController {

    private StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public Single<Student> addStudent(@RequestBody Student student){
        return studentService.add(student)
                .subscribeOn(Schedulers.io());
    }

    @GetMapping
    public Observable<Student> getAllStudents(){
        return studentService.getAll()
                .subscribeOn(Schedulers.io());

    }

    @GetMapping(value="/{studentId}")
    public Single<Student> getStudentById(@PathVariable Integer studentId){
        return studentService.getStudentById(studentId)
                .subscribeOn(Schedulers.io());
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
