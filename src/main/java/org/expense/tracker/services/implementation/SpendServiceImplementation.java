package org.expense.tracker.services.implementation;

import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.Income;
import org.expense.tracker.data.models.User;
import org.expense.tracker.data.repository.UserRepository;
import org.expense.tracker.dtos.request.*;
import org.expense.tracker.exceptions.InvalidCredentialsException;
import org.expense.tracker.exceptions.UserExistException;
import org.expense.tracker.exceptions.UserNotFoundException;
import org.expense.tracker.services.ExpenseService;
import org.expense.tracker.services.IncomeService;
import org.expense.tracker.services.ReportService;
import org.expense.tracker.services.SpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.expense.tracker.utils.Mappers.map;

@Service
public class SpendServiceImplementation implements SpendService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    IncomeService incomeService;
    @Autowired
    ExpenseService expenseService;
    @Autowired
    ReportService reportService;
    @Override
    public User register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getUsername())) throw  new UserExistException(registerRequest.getUsername() + " already exist");
        User user = map("UID"+(userRepository.count()+1),registerRequest.getUsername(),registerRequest.getPassword());
        userRepository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequest loginRequest) {
        User user = userRepository.findUserByUsername(loginRequest.getUsername());

        if (!userExist(loginRequest.getUsername())) throw new InvalidCredentialsException();
        if (!loginRequest.getPassword().equals(user.getPassword())) throw new InvalidCredentialsException();

        user.setLogin(true);
        userRepository.save(user);
    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        User user = userRepository.findUserByUsername(logoutRequest.getUsername());

        if (!userExist(logoutRequest.getUsername())) throw new InvalidCredentialsException();
        if (!logoutRequest.getPassword().equals(user.getPassword())) throw new InvalidCredentialsException();

        user.setLogin(false);
        userRepository.save(user);
    }

    @Override
    public User findAccountBelongingTo(String username) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) throw new UserNotFoundException(username + " not found");

        return user;
    }

    @Override
    public Income addIncome(IncomeRequest incomeRequest) {
        return incomeService.addIncome(incomeRequest.getUserId(),incomeRequest.getDate(),incomeRequest.getCategory(),incomeRequest.getAmount(),incomeRequest.getDescription());
    }

    @Override
    public Expense addExpense(ExpenseRequest expenseRequest) {
     return expenseService.addExpenses(expenseRequest.getUserId(), expenseRequest.getDate(),expenseRequest.getCategory(),expenseRequest.getAmount(), expenseRequest.getDescription());
    }

    @Override
    public List<Object> transaction(String userId) {
        return reportService.transactionList(userId);
    }

    @Override
    public List<Income> incomeHistory(String userId) {
        return incomeService.viewIncome(userId);
    }

    private boolean userExist(String username){
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }
}
