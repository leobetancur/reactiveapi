package com.reactive.student.infrastructure.handleerrors;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ErrorDTO {
    private Integer status;
    private String error;
    private LocalDate timestamp;
}
