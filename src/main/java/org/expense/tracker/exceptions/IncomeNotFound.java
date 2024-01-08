package org.expense.tracker.exceptions;

public class IncomeNotFound extends RuntimeException{
    public IncomeNotFound(String message){
        super(message);
    }
}
