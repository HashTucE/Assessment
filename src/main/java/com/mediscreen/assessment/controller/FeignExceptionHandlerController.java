package com.mediscreen.assessment.controller;

import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FeignExceptionHandlerController {


    private static final Logger log = LogManager.getLogger(FeignExceptionHandlerController.class);

//    /**
//     * Handle FeignException response entity.
//     * @param ex the ex
//     * @return the response entity
//     */
//    @ExceptionHandler(FeignException.NotFound.class)
//    public ResponseEntity<String> handleFeignException(FeignException ex) {
//        log.error("FeignException", ex);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient does not exist with id : " + ex.getMessage());
//    }
}
