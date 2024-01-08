package org.expense.tracker.exceptions;

public class ExpenseDetailsInvalid extends RuntimeException{
    public ExpenseDetailsInvalid(String message) {
        super(message);
    }
}
