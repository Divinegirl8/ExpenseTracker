package org.expense.tracker.services;

import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.Income;
import org.expense.tracker.data.models.User;
import org.expense.tracker.dtos.request.*;

import java.util.List;

public interface SpendService {
    User register(RegisterRequest registerRequest);
    void  login(LoginRequest loginRequest);
    void logout(LogoutRequest logoutRequest);
    User findAccountBelongingTo(String username);
    Income addIncome(IncomeRequest incomeRequest);
    Expense addExpense(ExpenseRequest expenseRequest);
    List<Object> transaction(String userId);
    List<Income> incomeHistory(String userId);
}
