package org.expense.tracker.dtos.request;

import lombok.Data;
import org.expense.tracker.data.models.Category;
import org.expense.tracker.data.models.Date;
import org.expense.tracker.data.models.IncomeCategory;

import java.math.BigDecimal;

@Data
public class ExpenseRequest {
    private String userId;
    private Date date;
    private Category category;
    private BigDecimal amount = BigDecimal.ZERO;
    private String description;
}
