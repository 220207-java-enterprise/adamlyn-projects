package com.revature.spring.util.exceptions;

public class AuthenticationException extends ResourceNotFoundException {

    public AuthenticationException() {
        super("No user found using the provided credentials.");
    }

    public AuthenticationException(String msg){
        super(msg);
    }

}
