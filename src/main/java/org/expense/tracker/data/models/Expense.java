package org.expense.tracker.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Expense {
    @Id
    private String expenseId;
    private String userId;
    private Date date;
    private Category category;
    private BigDecimal amount = BigDecimal.ZERO;
    private String description;
}
