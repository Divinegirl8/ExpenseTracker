package org.expense.tracker.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Expense {
    @Id
    private String expenseId;
    private Date date;
    private Category category;
    private BigDecimal amount;
    private String description;
}
