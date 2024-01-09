package org.expense.tracker.controller;

import org.expense.tracker.data.models.Expense;
import org.expense.tracker.data.models.Income;
import org.expense.tracker.dtos.request.FindExpenseRequest;
import org.expense.tracker.dtos.request.FindIncomeRequest;
import org.expense.tracker.dtos.request.IncomeRequest;
import org.expense.tracker.dtos.request.RemoveIncomeRequest;
import org.expense.tracker.dtos.response.*;
import org.expense.tracker.services.SpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class IncomeTrackerController {
    @Autowired
    SpendService spendService;

    @PostMapping("/addIncome")
    public ResponseEntity<?> addIncome(@RequestBody IncomeRequest incomeRequest) {
        AddIncomeResponse addIncomeResponse = new AddIncomeResponse();
        try {
            Income income = spendService.addIncome(incomeRequest);
            addIncomeResponse.setMessage("Income id is " + income);
            return new ResponseEntity<>(new ApiResponse(true, addIncomeResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            addIncomeResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, addIncomeResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/totalIncome/{userId}")
    public ResponseEntity<?> totalIncome(@PathVariable ("userId") String userId){
        TotalResponse totalResponse = new TotalResponse();

        try {
            BigDecimal amount = spendService.totalIncome(userId);
            totalResponse.setMessage(amount+"");
            return new ResponseEntity<>(new ApiResponse(true, totalResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            totalResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, totalResponse), HttpStatus.BAD_REQUEST);
        }
        }

        @DeleteMapping("/removeIncome")
        public ResponseEntity<?> removeIncome(@RequestBody RemoveIncomeRequest removeIncomeRequest){
            RemoveIncomeResponse removeIncomeResponse = new RemoveIncomeResponse();

            try {
                spendService.removeIncome(removeIncomeRequest);
                removeIncomeResponse.setMessage("Income removed");
                return new ResponseEntity<>(new ApiResponse(true,removeIncomeResponse), HttpStatus.CREATED);
            } catch (Exception exception) {
                removeIncomeResponse.setMessage(exception.getMessage());
                return new ResponseEntity<>(new ApiResponse(false, removeIncomeResponse), HttpStatus.BAD_REQUEST);
            }
        }

    @GetMapping("/findIncome")
    public  ResponseEntity<?> findIncome(@RequestBody FindIncomeRequest findIncomeRequest){
        FindIncomeResponse findIncomeResponse = new FindIncomeResponse();
        try {
            Income income = spendService.findIncome(findIncomeRequest);
            findIncomeResponse.setMessage(income+"");
            return new ResponseEntity<>(new ApiResponse(true, findIncomeResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            findIncomeResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, findIncomeResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/viewIncome/{userId}")
    public  ResponseEntity<?> viewIncome(@PathVariable("userId") String userId){
        ViewIncomeResponse viewIncomeResponse = new ViewIncomeResponse();
        try {
            List<Income> expense = spendService.viewIncome(userId);
            viewIncomeResponse.setMessage(expense+"");
            return new ResponseEntity<>(new ApiResponse(true, viewIncomeResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            viewIncomeResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, viewIncomeResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeAllIncome/{userId}")
    public ResponseEntity<?> removeAllIncome(@PathVariable("userId") String userId){
        RemoveIncomeResponse removeIncomeResponse = new RemoveIncomeResponse();
        try {
            spendService.removeAllIncome(userId);
            removeIncomeResponse.setMessage("All incomes removed successfully");
            return new ResponseEntity<>(new ApiResponse(true,removeIncomeResponse),HttpStatus.ACCEPTED);
        }catch (Exception exception){
            removeIncomeResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,removeIncomeResponse),HttpStatus.BAD_REQUEST);
        }
    }


}

