package com.reactive.student.service;

import com.reactive.student.model.Student;
import com.reactive.student.web.controller.NewInfoStudent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student newInfoStudentToStudent(NewInfoStudent newInfoStudent);
}
