package org.expense.tracker.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Transaction {
    @Id
    private String userId;
    private Date date;
    private BigDecimal amount;
    private String description;
}
