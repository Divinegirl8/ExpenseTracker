package org.expense.tracker.services;

import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.Income;
import org.expense.tracker.data.models.User;
import org.expense.tracker.dtos.request.*;

import java.math.BigDecimal;
import java.util.List;

public interface SpendService {
    User register(RegisterRequest registerRequest);
    void  login(LoginRequest loginRequest);
    void logout(LogoutRequest logoutRequest);
    User findAccountBelongingTo(String username);
    Income addIncome(IncomeRequest incomeRequest);
    Expense addExpense(ExpenseRequest expenseRequest);
    List<Object> transaction(String userId);
    BigDecimal totalIncome(String userId);
    BigDecimal totalExpense(String userId);
    BigDecimal balance(String userId);
    void removeIncome(String incomeId,String userId);

    void removeExpense(String expenseId,String userId);
    Income findIncome(String incomeId, String userId);
    Expense findExpense(String expenseId, String userId);
    List<Expense> viewExpense(String userId);
    List<Income> viewIncome(String userId);
    void removeAllExpense(String userId);
    void removeAllIncome(String userId);
}
