package com.example.test.exception;

public class WalletNotExistException extends RuntimeException{
    public WalletNotExistException(String message){
        super(message);
    }
}
