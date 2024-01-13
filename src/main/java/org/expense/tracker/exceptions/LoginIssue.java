package org.expense.tracker.exceptions;

public class LoginIssue extends RuntimeException{
    public LoginIssue(String message){
        super(message);
    }
}
