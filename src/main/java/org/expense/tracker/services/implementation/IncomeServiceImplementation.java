package org.expense.tracker.services.implementation;

import org.expense.tracker.data.models.Date;
import org.expense.tracker.data.models.Income;
import org.expense.tracker.data.models.IncomeCategory;
import org.expense.tracker.data.models.User;
import org.expense.tracker.data.repository.IncomeRepository;
import org.expense.tracker.data.repository.UserRepository;
import org.expense.tracker.exceptions.IncomeNotFound;
import org.expense.tracker.exceptions.UserNotFoundException;
import org.expense.tracker.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeServiceImplementation implements IncomeService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    IncomeRepository incomeRepository;
    @Override
    public Income addIncome(String userId,Date date, IncomeCategory category, BigDecimal amount, String description) {
        User user = userRepository.findByUserId(userId);
        Income income = new Income();

        if (user == null) throw new UserNotFoundException(userId + " not found");


        income.setUserId(userId);
        income.setIncomeId("IID" + (incomeRepository.count() + 1));
        income.setDate(date);
        income.setCategory(category);
        income.setAmount(amount);
        income.setDescription(description);

        incomeRepository.save(income);

        return income;
    }

    @Override
    public void removeIncome(String incomeId, String userId) {
     Income income = incomeRepository.findIncomeByIncomeIdAndUserId(incomeId,userId);

     if (income == null) throw new IncomeNotFound("income details is not valid");

     incomeRepository.delete(income);

    }

    @Override
    public List<Income> viewIncome(String userId) {
        List<Income> incomeList = incomeRepository.findIncomeByUserId(userId);

        if (incomeList.isEmpty()) throw new IncomeNotFound("income not found");
        return incomeList;
    }

    @Override
    public void removeAllIncome(String userId) {
     List<Income> incomeList = incomeRepository.findIncomeByUserId(userId);

        if (incomeList.isEmpty()) throw new IncomeNotFound("income not found");
        incomeRepository.deleteAll(incomeList);
    }

    @Override
    public BigDecimal addIncomeAmount(String userId) {
        List<Income> incomeList = incomeRepository.findIncomeByUserId(userId);

        if (incomeList.isEmpty()) throw new IncomeNotFound("income not found");

        BigDecimal total = BigDecimal.ZERO;

        for (Income income : incomeList){
            total = total.add(income.getAmount());
        }
        return total;
    }

    @Override
    public Income findIncome(String incomeId, String userId) {
        Income income = incomeRepository.findIncomeByIncomeIdAndUserId(incomeId,userId);
        if (income == null) throw new IncomeNotFound("Income not found");
        return income;
    }
}
