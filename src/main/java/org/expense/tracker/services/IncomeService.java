package org.expense.tracker.services;

import org.expense.tracker.data.models.*;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeService {
    Income addIncome(String userId, Date date, IncomeCategory category, BigDecimal amount, String description);
    void removeIncome(String incomeId, String userId);

    List<Income> viewIncome(String userId);
    void removeAllIncome(String userId);

    BigDecimal addIncomeAmount(String userId);

    Income findIncome(String incomeId, String userId);
}
