package org.expense.tracker.dtos.request;

import lombok.Data;

@Data
public class RemoveExpenseRequest {
    private String ExpenseId;
    private String userId;
}
