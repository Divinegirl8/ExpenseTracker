package org.expense.tracker.dtos.request;

import lombok.Data;

@Data
public class FindExpenseRequest {
    private String ExpenseId;
    private String userId;
}
