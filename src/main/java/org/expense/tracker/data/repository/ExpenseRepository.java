package org.expense.tracker.data.repository;

import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense,String> {
    Expense findExpenseByExpenseIdAndUserId(String expenseId,String userId);
    List<Expense> findExpenseByUserId(String userId);


}
