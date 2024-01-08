package org.expense.tracker.data.repository;

import org.expense.tracker.data.models.Income;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IncomeRepository extends MongoRepository<Income,String> {
    Income findIncomeByIncomeIdAndUserId(String incomeId,String userId);
    List<Income> findIncomeByUserId(String userId);
}
