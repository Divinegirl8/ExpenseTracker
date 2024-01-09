package org.expense.tracker.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class User {
    @Id
    private String userId;
    private String username;
    private String password;
    private boolean login;
}
