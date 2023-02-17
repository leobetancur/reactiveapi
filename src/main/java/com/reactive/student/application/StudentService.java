package com.reactive.student.application;

import com.reactive.student.domain.entities.Student;
import com.reactive.student.domain.exception.ExceptionDataBase;
import com.reactive.student.domain.exception.StudentDoestExist;
import com.reactive.student.domain.repository.StudentCache;
import com.reactive.student.domain.repository.StudentRepository;
import com.reactive.student.domain.entities.NewInfoStudent;
import io.reactivex.rxjava3.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final StudentCache studentCache;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          StudentCache studentCache,
                          StudentMapper studentMapper){
        this.studentRepository = studentRepository;
        this.studentCache = studentCache;
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
        return studentCache.findById(studentId)
                .switchIfEmpty( studentRepository.findById(studentId)
                    .doAfterSuccess(student->studentCache.save(student))
                    .switchIfEmpty(Single.error(new StudentDoestExist()))
                    .doOnError(t->logger.error(t.getMessage())));
    }

    public Completable deleteStudentById(Integer studentId){
        return studentRepository.existsById(studentId)
                .flatMapCompletable(studentExist->{
                    if(!studentExist) {
                        return Completable.error(new StudentDoestExist());
                    }
                    studentCache.deleteById(studentId);
                    return studentRepository.deleteById(studentId);
                })
                .doOnError(t->logger.error(t.getMessage()));
    }

    public Completable updateStudent(Student student){
        return studentRepository.existsById(student.getId())
                .flatMapCompletable(studentExist->{
                    if(!studentExist){
                        return Completable.error(new StudentDoestExist());
                    }
                    return studentRepository.save(student).ignoreElement();
                })
                .doOnError(t->logger.error(t.getMessage()));
    }

    public Completable deleteAllInactive(){
        return studentRepository.deleteAll(studentRepository.findAll()
                .filter(student -> Boolean.FALSE.equals(student.getActive()))
                .doOnNext(student->studentCache.deleteById(student.getId()))
        );

    }

    public Maybe<Integer> sumAges(Integer studentId1, Integer studentId2){
        return Observable.merge(getStudentById(studentId1).toObservable(), getStudentById(studentId2).toObservable())
                .map(Student::getAge)
                .reduce( (e1,e2)-> e1 + e2 );
    }

    public Maybe<Student> createNewWithBaseAnothers(Integer studentId1, Integer studentId2){
        return Observable.zip(getStudentById(studentId1).toObservable(), getStudentById(studentId2).toObservable() , (student1,student2) ->{
            NewInfoStudent newStudent = NewInfoStudent.builder()
                    .name(student1.getName()+ " " + student2.getName())
                    .active(Boolean.TRUE)
                    .age((student1.getAge() + student2.getAge())/2)
                    .build();
            return newStudent;
        }).flatMapSingle(newStudent->add(newStudent)).firstElement();
    }


}
