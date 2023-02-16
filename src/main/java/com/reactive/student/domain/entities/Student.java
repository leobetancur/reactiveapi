package com.reactive.student.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
public class Student {

    @Id
    private Integer id;
    private String name;
    private Boolean active;
}
