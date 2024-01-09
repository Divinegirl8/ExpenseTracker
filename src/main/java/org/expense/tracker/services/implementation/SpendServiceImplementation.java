package org.expense.tracker.services.implementation;

import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.Income;
import org.expense.tracker.data.models.User;
import org.expense.tracker.data.repository.UserRepository;
import org.expense.tracker.dtos.request.*;
import org.expense.tracker.exceptions.IncomeAmountIsLess;
import org.expense.tracker.exceptions.InvalidCredentialsException;
import org.expense.tracker.exceptions.UserExistException;
import org.expense.tracker.exceptions.UserNotFoundException;
import org.expense.tracker.services.ExpenseService;
import org.expense.tracker.services.IncomeService;
import org.expense.tracker.services.ReportService;
import org.expense.tracker.services.SpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public BigDecimal totalIncome(String userId) {
        return incomeService.addIncomeAmount(userId);
    }

    @Override
    public BigDecimal totalExpense(String userId) {
        return expenseService.addExpenseAmount(userId);
    }

    @Override
    public BigDecimal balance(String userId) {
        BigDecimal incomeAmount = incomeService.addIncomeAmount(userId);
        BigDecimal expenseAmount = expenseService.addExpenseAmount(userId);

        if (incomeAmount.compareTo(expenseAmount) < 0) {
            throw new IncomeAmountIsLess("Expense amount is greater than income amount");
        }
        return incomeAmount.subtract(expenseAmount);
    }

    @Override
    public void removeIncome(RemoveIncomeRequest removeIncomeRequest) {
     incomeService.removeIncome(removeIncomeRequest.getIncomeId(),removeIncomeRequest.getUserId());
    }

    @Override
    public void removeExpense(RemoveExpenseRequest removeExpenseRequest) {
      expenseService.removeExpense(removeExpenseRequest.getExpenseId(),removeExpenseRequest.getUserId());
    }

    @Override
    public Income findIncome(FindIncomeRequest findIncomeRequest) {
        return incomeService.findIncome(findIncomeRequest.getIncomeId(), findIncomeRequest.getUserId());
    }

    @Override
    public Expense findExpense(FindExpenseRequest findExpenseRequest) {
        return expenseService.findExpense(findExpenseRequest.getExpenseId(), findExpenseRequest.getUserId());
    }

    @Override
    public List<Expense> viewExpense(String userId) {
        return expenseService.viewExpense(userId);
    }

    @Override
    public List<Income> viewIncome(String userId) {
        return incomeService.viewIncome(userId);
    }

    @Override
    public void removeAllExpense(String userId) {
        expenseService.removeAllExpense(userId);
    }

    @Override
    public void removeAllIncome(String userId) {
        incomeService.removeAllIncome(userId);
    }


    private boolean userExist(String username){
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }
}
