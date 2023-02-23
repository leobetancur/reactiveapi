package com.reactive.student.infrastructure.handleerrors;


import com.reactive.student.domain.exception.ExceptionWithCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class ExceptionRestController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionRestController.class);

    @Autowired
    private ErrorCodes errorCodes;

    @ExceptionHandler(ExceptionWithCode.class)
    public ResponseEntity<ErrorDTO> handleExceptionWithCode(ExceptionWithCode exceptionWithCode){
        return ResponseEntity.status(errorCodes.getHttpStatusCode(exceptionWithCode.getCode())).body(buildErrorDTO(exceptionWithCode));
    }

    private ErrorDTO buildErrorDTO(ExceptionWithCode exceptionWithCode){
        return ErrorDTO.builder()
                .status(errorCodes.getHttpStatusCode(exceptionWithCode.getCode()))
                .error(errorCodes.getErrorMessage(exceptionWithCode.getCode()))
                .timestamp(LocalDate.now())
                .build();
    }
}
