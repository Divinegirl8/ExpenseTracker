package org.expense.tracker.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(){
        System.out.println("Credentials is not valid");
    }
}
