package org.expense.tracker.services.implementation;

import lombok.Data;
import org.expense.tracker.data.models.Category;
import org.expense.tracker.data.models.Date;
import org.expense.tracker.data.models.IncomeCategory;
import org.expense.tracker.data.repository.ExpenseRepository;
import org.expense.tracker.data.repository.IncomeRepository;
import org.expense.tracker.data.repository.UserRepository;
import org.expense.tracker.dtos.request.*;
import org.expense.tracker.exceptions.*;
import org.expense.tracker.services.SpendService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SpendServiceImplementationTest {
    @Autowired
    SpendService spendService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    IncomeRepository incomeRepository;

    @AfterEach
    void  cleanUp(){
        userRepository.deleteAll();
        expenseRepository.deleteAll();
        incomeRepository.deleteAll();
    }

    @Test
    void register_User_Count_Is_One(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertEquals(1,userRepository.count());

    }

    @Test void register_User_With_Same_Username(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertThrows(UserExistException.class,()-> spendService.register(registerRequest));

    }

    @Test void register_User_And_Login(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);

        assertTrue(spendService.findAccountBelongingTo("username").isLogin());
    }

    @Test void register_User_And_Login_With_Wrong_Username(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("name");
        loginRequest.setPassword("password");


        assertThrows(InvalidCredentialsException.class,()-> spendService.login(loginRequest));
    }

    @Test void register_User_And_Login_With_Wrong_Password(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("word");


        assertThrows(InvalidCredentialsException.class,()-> spendService.login(loginRequest));
    }
    @Test void register_User_Login_And_Logout(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");
        spendService.logout(logoutRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

    }

    @Test void register_User_Login_And_Logout_With_Wrong_Username(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("name");
        logoutRequest.setPassword("password");

        assertThrows(InvalidCredentialsException.class,()->  spendService.logout(logoutRequest));
    }


    @Test void register_User_Login_And_Logout_With_Wrong_Password(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("word");

        assertThrows(InvalidCredentialsException.class,()->  spendService.logout(logoutRequest));
    }


    @Test void register_User_Login_And_AddIncome(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);


        assertNotNull(spendService.transaction("UID1"));
    }

    @Test void register_User_Login_And_AddIncome_AddExpense(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setUserId("UID1");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setAmount(BigDecimal.valueOf(2000));
        expenseRequest.setDescription("Clothes");
        expenseRequest.setDate(date);
       spendService.addExpense(expenseRequest);
       spendService.addIncome(incomeRequest);

        assertNotNull(spendService.transaction("UID1"));
    }

    @Test void register_User_Login_And_AddIncome_AddExpense_Get_Total_Income_Amount(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setUserId("UID1");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setAmount(BigDecimal.valueOf(2000));
        expenseRequest.setDescription("Clothes");
        expenseRequest.setDate(date);
        spendService.addExpense(expenseRequest);


        assertNotNull(spendService.transaction("UID1"));
        assertEquals(BigDecimal.valueOf(4000),spendService.totalIncome("UID1"));
    }

    @Test void register_User_Login_And_AddIncome_AddExpense_Get_Total_Expense_Amount(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setUserId("UID1");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setAmount(BigDecimal.valueOf(2000));
        expenseRequest.setDescription("Clothes");
        expenseRequest.setDate(date);
        spendService.addExpense(expenseRequest);


        assertNotNull(spendService.transaction("UID1"));
        assertEquals(BigDecimal.valueOf(2000),spendService.totalExpense("UID1"));
    }

    @Test void register_User_Login_And_AddIncome_AddExpense_Get_Total_Balance(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        assertEquals(BigDecimal.valueOf(4000),spendService.totalIncome("UID1"));

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setUserId("UID1");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setAmount(BigDecimal.valueOf(2000));
        expenseRequest.setDescription("Clothes");
        expenseRequest.setDate(date);
        spendService.addExpense(expenseRequest);


        assertNotNull(spendService.transaction("UID1"));
        assertEquals(BigDecimal.valueOf(2000),spendService.totalExpense("UID1"));
        assertEquals(BigDecimal.valueOf(2000),spendService.balance("UID1"));
    }


    @Test void register_User_Login_And_AddIncome_AddExpense_Get_Total_Balance_Two(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);
        assertEquals(BigDecimal.valueOf(1000),spendService.totalIncome("UID1"));

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setUserId("UID1");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setAmount(BigDecimal.valueOf(500));
        expenseRequest.setDescription("Clothes");
        expenseRequest.setDate(date);
        spendService.addExpense(expenseRequest);
        spendService.addExpense(expenseRequest);


        assertNotNull(spendService.transaction("UID1"));
        assertEquals(BigDecimal.valueOf(1000),spendService.totalExpense("UID1"));
        assertEquals(BigDecimal.valueOf(0),spendService.balance("UID1"));
    }

    @Test void register_User_Login_And_AddIncome_AddExpense_If_Expense_Amount_Is_Greater_Throws_Error(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);
        assertEquals(BigDecimal.valueOf(1000),spendService.totalIncome("UID1"));

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setUserId("UID1");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setAmount(BigDecimal.valueOf(500));
        expenseRequest.setDescription("Clothes");
        expenseRequest.setDate(date);
        spendService.addExpense(expenseRequest);
        spendService.addExpense(expenseRequest);
        spendService.addExpense(expenseRequest);


        assertNotNull(spendService.transaction("UID1"));
        assertEquals(BigDecimal.valueOf(1500),spendService.totalExpense("UID1"));
        assertThrows(IncomeAmountIsLess.class,()->spendService.balance("UID1"));
    }


    @Test void register_User_Login_And_AddIncome_AddExpense_Remove_Income(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        assertEquals(BigDecimal.valueOf(2000),spendService.totalIncome("UID1"));

        RemoveIncomeRequest removeIncomeRequest = new RemoveIncomeRequest();
        removeIncomeRequest.setIncomeId("IID1");
        removeIncomeRequest.setUserId("UID1");
        spendService.removeIncome(removeIncomeRequest);

        FindIncomeRequest findIncomeRequest = new FindIncomeRequest();
        findIncomeRequest.setIncomeId("IID1");
        findIncomeRequest.setUserId("UID1");
        assertThrows(IncomeNotFound.class,()-> spendService.findIncome(findIncomeRequest));



    }
    @Test void register_User_Login_And_AddIncome_AddExpense_Remove_Expense(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        spendService.register(registerRequest);
        assertFalse(spendService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        spendService.login(loginRequest);
        assertTrue(spendService.findAccountBelongingTo("username").isLogin());

        IncomeRequest incomeRequest = new IncomeRequest();
        incomeRequest.setUserId("UID1");
        incomeRequest.setCategory(IncomeCategory.SALARY);
        incomeRequest.setAmount(BigDecimal.valueOf(1000));
        incomeRequest.setDescription("salary");

        Date date = new Date();
        date.setDay("10");
        date.setMonth("05");
        date.setYear("2021");
        incomeRequest.setDate(date);

        spendService.addIncome(incomeRequest);
        spendService.addIncome(incomeRequest);
        assertEquals(BigDecimal.valueOf(2000),spendService.totalIncome("UID1"));

        ExpenseRequest expenseRequest = new ExpenseRequest();
        expenseRequest.setUserId("UID1");
        expenseRequest.setCategory(Category.CLOTHES);
        expenseRequest.setAmount(BigDecimal.valueOf(500));
        expenseRequest.setDescription("Clothes");
        expenseRequest.setDate(date);
        spendService.addExpense(expenseRequest);
        spendService.addExpense(expenseRequest);
        spendService.addExpense(expenseRequest);

        RemoveExpenseRequest removeExpenseRequest = new RemoveExpenseRequest();
        removeExpenseRequest.setExpenseId("EID2");
        removeExpenseRequest.setUserId("UID1");

        spendService.removeExpense(removeExpenseRequest);

        FindExpenseRequest findExpenseRequest = new FindExpenseRequest();
        findExpenseRequest.setExpenseId("EID2");
        findExpenseRequest.setUserId("UID1");
        assertThrows(ExpenseNotFound.class,()-> spendService.findExpense(findExpenseRequest));



    }



}