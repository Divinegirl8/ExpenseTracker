package org.expense.tracker.exceptions;

public class ExpenseNotFound extends RuntimeException{
    public ExpenseNotFound(String message){
        super(message);
    }
}
