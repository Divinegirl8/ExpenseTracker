package org.expense.tracker.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(){
        super("Credentials is not valid");
    }
}
