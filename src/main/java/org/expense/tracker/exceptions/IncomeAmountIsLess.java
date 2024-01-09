package org.expense.tracker.exceptions;

public class IncomeAmountIsLess extends RuntimeException{
    public IncomeAmountIsLess(String message){
        super(message);
    }
}
