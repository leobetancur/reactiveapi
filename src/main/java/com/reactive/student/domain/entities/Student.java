package com.reactive.student.domain.entities;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@AllArgsConstructor
public class Student {

    @Id
    private Integer id;
    private String name;
    private Boolean active;
    private Integer age;
}
