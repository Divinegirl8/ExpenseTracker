package org.expense.tracker.controller;

import org.expense.tracker.data.models.Income;
import org.expense.tracker.dtos.request.IncomeRequest;
import org.expense.tracker.dtos.response.AddIncomeResponse;
import org.expense.tracker.dtos.response.ApiResponse;
import org.expense.tracker.dtos.response.TotalResponse;
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
    }

