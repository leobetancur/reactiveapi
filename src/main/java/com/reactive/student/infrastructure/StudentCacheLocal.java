package com.reactive.student.infrastructure;

import com.reactive.student.domain.entities.Student;
import com.reactive.student.domain.repository.StudentCache;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentCacheLocal implements StudentCache {
    private final Logger logger = LoggerFactory.getLogger(StudentCacheLocal.class);

    private Map<Integer, Student> studentMap = new HashMap();

    @Override
    public void save(Student student) {
        logger.info("Saving in local cache");
        studentMap.put(student.getId(),student);
    }

    @Override
    public Maybe<Student> findById(Integer id) {
        logger.info("Getting from local cache");
        Student student = studentMap.get(id);
        if(student==null){
            return Maybe.empty();
        }
        return Maybe.just(student);
    }

    @Override
    public Completable deleteById(Integer id){
        if(studentMap.containsKey(id)){
            logger.info("Deleting from local cache");
            studentMap.remove(id);
        }
        return Completable.complete();
    }
}
