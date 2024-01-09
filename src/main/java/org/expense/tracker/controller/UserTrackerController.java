package org.expense.tracker.controller;

import org.expense.tracker.data.models.User;
import org.expense.tracker.dtos.request.LoginRequest;
import org.expense.tracker.dtos.request.LogoutRequest;
import org.expense.tracker.dtos.request.RegisterRequest;
import org.expense.tracker.dtos.response.*;
import org.expense.tracker.services.SpendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class UserTrackerController {
    @Autowired
    SpendService spendService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            User user = spendService.register(registerRequest);
            registerResponse.setMessage("Your Id is " + user);
            return new ResponseEntity<>(new ApiResponse(true, registerResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            registerResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, registerResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            spendService.login(loginRequest);
            loginResponse.setMessage("you have login");
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            loginResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, loginResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        LogoutResponse logoutResponse = new LogoutResponse();
        try {
            spendService.logout(logoutRequest);
            logoutResponse.setMessage("you have logout");
            return new ResponseEntity<>(new ApiResponse(true, logoutResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            logoutResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, logoutResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/transaction/{userId}")
    public ResponseEntity<?> transaction(@PathVariable("userId") String userId){
        TransactionResponse transactionResponse = new TransactionResponse();

        try {
           List<Object> objectList = spendService.transaction(userId);
            transactionResponse.setMessage(objectList+"");
            return new ResponseEntity<>(new ApiResponse(true, transactionResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            transactionResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, transactionResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<?> balance(@PathVariable("userId") String userId){
        BalanceResponse balanceResponse = new BalanceResponse();

        try {
            BigDecimal balance = spendService.balance(userId);
            balanceResponse.setMessage(balance+"");
            return new ResponseEntity<>(new ApiResponse(true, balanceResponse), HttpStatus.CREATED);
        } catch (Exception exception) {
            balanceResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, balanceResponse), HttpStatus.BAD_REQUEST);
        }
    }
}
