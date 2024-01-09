package org.expense.tracker.dtos.request;

import lombok.Data;

@Data
public class RemoveIncomeRequest {
    private String incomeId;
    private String userId;
}
