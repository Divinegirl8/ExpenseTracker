package org.expense.tracker.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Income {
    @Id
    private String incomeId;
    private Date date;
    private IncomeCategory category;
    private BigDecimal amount;
    private String description;

}
