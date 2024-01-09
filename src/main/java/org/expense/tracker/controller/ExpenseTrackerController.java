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
import java.util.List;

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

    @DeleteMapping("/removeExpense")
    public  ResponseEntity<?> removeExpense(@RequestBody RemoveExpenseRequest removeExpenseRequest){
        RemoveExpenseResponse removeExpenseResponse = new RemoveExpenseResponse();
        try {
            spendService.removeExpense(removeExpenseRequest);
            removeExpenseResponse.setMessage("Expense removed");
            return new ResponseEntity<>(new ApiResponse(true, removeExpenseResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            removeExpenseResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, removeExpenseResponse), HttpStatus.BAD_REQUEST);
        }
        }
    @GetMapping("/findExpense")
    public  ResponseEntity<?> findExpense(@RequestBody FindExpenseRequest findExpenseRequest){
        FindExpenseResponse findExpenseResponse = new FindExpenseResponse();
        try {
           Expense expense = spendService.findExpense(findExpenseRequest);
            findExpenseResponse.setMessage(expense+"");
            return new ResponseEntity<>(new ApiResponse(true, findExpenseResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            findExpenseResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, findExpenseResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/viewExpense/{userId}")
    public  ResponseEntity<?> viewExpense(@PathVariable("userId") String userId){
        ViewExpenseResponse viewExpenseResponse = new ViewExpenseResponse();
        try {
            List<Expense> expense = spendService.viewExpense(userId);
            viewExpenseResponse.setMessage(expense+"");
            return new ResponseEntity<>(new ApiResponse(true, viewExpenseResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            viewExpenseResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, viewExpenseResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeAllExpense/{userId}")
    public ResponseEntity<?> removeAllExpense(@PathVariable("userId") String userId){
        RemoveExpenseResponse removeExpenseResponse = new RemoveExpenseResponse();
        try {
            spendService.removeAllExpense(userId);
            removeExpenseResponse.setMessage("All expenses removed successfully");
            return new ResponseEntity<>(new ApiResponse(true,removeExpenseResponse),HttpStatus.ACCEPTED);
        }catch (Exception exception){
            removeExpenseResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,removeExpenseResponse),HttpStatus.BAD_REQUEST);
        }
    }


    }



