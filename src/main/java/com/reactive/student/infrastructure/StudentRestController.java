package com.reactive.student.infrastructure;

import com.reactive.student.domain.entities.Student;
import com.reactive.student.application.StudentService;
import com.reactive.student.domain.entities.NewInfoStudent;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/students")
public class StudentRestController {

    private static final Logger logger = LoggerFactory.getLogger(StudentRestController.class);

    private StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public Single<Student> addStudent(@RequestBody NewInfoStudent newInfoStudent){
        return studentService.add(newInfoStudent)
                .subscribeOn(Schedulers.io());
    }

    @GetMapping
    public Observable<Student> getActiveStudents(){
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

    @PutMapping(value="/{studentId}")
    public Completable updateStudent(@PathVariable Integer studentId, @RequestBody NewInfoStudent newInfoStudent ){
        return studentService.updateStudent(Student.builder()
                        .id(studentId)
                        .name(newInfoStudent.getName())
                        .active(newInfoStudent.getActive())
                        .age(newInfoStudent.getAge())
                .build())
                .subscribeOn(Schedulers.io());
    }

    @GetMapping(value="/sum")
    public Maybe<Integer> sumAges(@RequestParam Integer studentId1, @RequestParam Integer studentId2){
        return studentService.sumAges(studentId1, studentId2)
                .subscribeOn(Schedulers.io());
    }

    @DeleteMapping("/all")
    public Completable deleteAllInactive(){
        return studentService.deleteAllInactive()
                .subscribeOn(Schedulers.io());
    }

    @PostMapping("/createNewWithBaseAnothers")
    public Maybe<Student> createNewWithBaseAnothers(@RequestParam Integer studentId1, @RequestParam Integer studentId2){
        return studentService.createNewWithBaseAnothers(studentId1,studentId2)
                .subscribeOn(Schedulers.io());
    }
}
