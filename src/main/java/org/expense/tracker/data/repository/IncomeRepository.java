package org.expense.tracker.data.repository;

import org.expense.tracker.data.models.Income;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IncomeRepository extends MongoRepository<Income,String> {
}
