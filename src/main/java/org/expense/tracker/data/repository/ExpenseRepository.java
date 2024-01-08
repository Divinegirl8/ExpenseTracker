package org.expense.tracker.data.repository;

import org.expense.tracker.data.models.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense,String> {
}
