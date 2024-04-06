package com.example.test.exception;

import com.example.test.exception.error.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchWalletNotExistException(WalletNotExistException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchInsufficientFundsException(InsufficientFundsException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }
}
