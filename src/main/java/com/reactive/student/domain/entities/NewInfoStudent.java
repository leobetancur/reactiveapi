package com.reactive.student.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class NewInfoStudent {
    @NonNull
    private String name;
    @NonNull
    private Boolean active;
}
