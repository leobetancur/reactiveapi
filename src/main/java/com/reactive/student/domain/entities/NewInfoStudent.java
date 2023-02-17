package com.reactive.student.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
@Builder
public class NewInfoStudent {
    @NonNull
    private String name;
    @NonNull
    private Boolean active;
    @NonNull
    private Integer age;
}
