package org.expense.tracker.services.implementation;

import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.Income;
import org.expense.tracker.data.models.User;
import org.expense.tracker.data.repository.ExpenseRepository;
import org.expense.tracker.data.repository.IncomeRepository;
import org.expense.tracker.data.repository.TransactionRepository;
import org.expense.tracker.data.repository.UserRepository;
import org.expense.tracker.exceptions.UserNotFoundException;
import org.expense.tracker.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ReportServiceImplementation implements ReportService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    IncomeRepository incomeRepository;
    @Override
    public List<Object> transactionList(String userId) {
        User user = userRepository.findByUserId(userId);

        if (user == null) throw new UserNotFoundException(userId + " not found");

        List<Object> transactionHistory = new ArrayList<>();

        List<Expense> expenseList = expenseRepository.findExpenseByUserId(userId);
        List<Income> incomeList = incomeRepository.findIncomeByUserId(userId);

        transactionHistory.addAll(expenseList);
        transactionHistory.addAll(incomeList);

        return transactionHistory;
    }
}
