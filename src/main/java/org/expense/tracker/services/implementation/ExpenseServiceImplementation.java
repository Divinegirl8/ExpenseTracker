package org.expense.tracker.services.implementation;

import org.expense.tracker.data.models.Category;
import org.expense.tracker.data.models.Date;
import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.User;
import org.expense.tracker.data.repository.ExpenseRepository;
import org.expense.tracker.data.repository.UserRepository;
import org.expense.tracker.exceptions.ExpenseDetailsInvalid;
import org.expense.tracker.exceptions.ExpenseNotFound;
import org.expense.tracker.exceptions.UserNotFoundException;
import org.expense.tracker.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseServiceImplementation implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Expense addExpenses(String userId, Date date, Category category, BigDecimal amount, String description) {
        User user = userRepository.findByUserId(userId);
        Expense expense = new Expense();

        if (user == null) throw new UserNotFoundException(userId + " not found");



        expense.setUserId(userId);
        expense.setExpenseId("EID" + (expenseRepository.count() + 1));
        expense.setDate(date);
        expense.setCategory(category);
        expense.setAmount(amount);
        expense.setDescription(description);

        expenseRepository.save(expense);

        return expense;
    }

    @Override
    public void removeExpense(String expenseId, String userId) {
     Expense expense = expenseRepository.findExpenseByExpenseIdAndUserId(expenseId,userId);
     if (expense == null) throw new ExpenseDetailsInvalid("Details is invalid");
     expenseRepository.delete(expense);
    }

    @Override
    public List<Expense> viewExpense(String userId) {
     List<Expense> expenseList = expenseRepository.findExpenseByUserId(userId);
     if (expenseList.isEmpty()) throw new ExpenseNotFound("Expense not found");
     return expenseList;
    }

    @Override
    public void removeAllExpense(String userId) {
        List<Expense> expenseList = expenseRepository.findExpenseByUserId(userId);
        if (expenseList.isEmpty()) throw new ExpenseNotFound("Expense not found");
        expenseRepository.deleteAll(expenseList);
    }

    @Override
    public BigDecimal addExpenseAmount(String userId) {
        List<Expense> expenseList = expenseRepository.findExpenseByUserId(userId);
        if (expenseList.isEmpty()) throw new ExpenseNotFound("Expense not found");

        BigDecimal total = BigDecimal.ZERO;

        for (Expense expense : expenseList){
            total = total.add(expense.getAmount());
        }

        return total;
    }


}
