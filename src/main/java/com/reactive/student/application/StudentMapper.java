package com.reactive.student.application;

import com.reactive.student.domain.entities.Student;
import com.reactive.student.domain.entities.NewInfoStudent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student newInfoStudentToStudent(NewInfoStudent newInfoStudent);
}
