package com.dietideals.dietideals24_25.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException ex) {
        
        return new ResponseEntity<>("Errore nel caricamento del file: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        System.out.println("======== PRE STACK === Si è verificato un errore: " + ex.getMessage());
        ex.printStackTrace();
        System.out.println("======== POST STACK === Si è verificato un errore: " + ex.getMessage());

        return new ResponseEntity<>("Si è verificato un errore: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}