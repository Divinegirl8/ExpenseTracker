package org.expense.tracker.services;

import lombok.Data;
import org.expense.tracker.data.models.*;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService {
    Expense addExpenses(String userId, Date date, Category category, BigDecimal amount,String description);
    void removeExpense(String expenseId, String userId);

    List<Expense> viewExpense(String userId);

    void removeAllExpense(String userId);

    BigDecimal addExpenseAmount(String userId);
    Expense findExpense(String expenseId, String userId);
}
