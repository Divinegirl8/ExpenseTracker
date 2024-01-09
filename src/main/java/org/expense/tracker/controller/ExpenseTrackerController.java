package org.expense.tracker.controller;

import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.Income;
import org.expense.tracker.data.models.User;
import org.expense.tracker.dtos.request.*;
import org.expense.tracker.dtos.response.*;
import org.expense.tracker.services.SpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class ExpenseTrackerController {
   @Autowired
   SpendService spendService;



    @PostMapping("/addExpense")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseRequest expenseRequest) {
       AddExpenseResponse addExpenseResponse = new AddExpenseResponse();
        try {
            Expense expense = spendService.addExpense(expenseRequest);
            addExpenseResponse.setMessage("Expense id is " + expense);
            return new ResponseEntity<>(new ApiResponse(true, addExpenseResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            addExpenseResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,addExpenseResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/totalExpense/{userId}")
    public ResponseEntity<?> totalExpense(@PathVariable("userId") String userId){
        TotalResponse totalResponse = new TotalResponse();

        try {
            BigDecimal amount = spendService.totalExpense(userId);
            totalResponse.setMessage(amount+"");
            return new ResponseEntity<>(new ApiResponse(true, totalResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            totalResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, totalResponse), HttpStatus.BAD_REQUEST);
        }
    }



}
