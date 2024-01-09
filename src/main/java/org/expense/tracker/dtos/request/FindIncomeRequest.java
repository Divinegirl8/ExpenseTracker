package org.expense.tracker.dtos.request;

import lombok.Data;

@Data
public class FindIncomeRequest {
    private String incomeId;
    private String userId;
}
