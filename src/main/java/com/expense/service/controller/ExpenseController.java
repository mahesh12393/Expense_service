package com.expense.service.controller;

import com.expense.service.dto.ExpenseDto;
import com.expense.service.services.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    @Autowired
    ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }


    @GetMapping("/expense/v1/")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@PathParam("user_id") @NonNull String user_id){
        try{
            List<ExpenseDto> expensesList = expenseService.getExpenses(user_id);
            return new ResponseEntity<>(expensesList, HttpStatus.OK);
        } catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path="/addExpense")
    public ResponseEntity<Boolean> addExpenses(@RequestHeader(value = "X-User-Id") @NonNull String userId, @RequestBody ExpenseDto expenseDto){
        try{
            expenseDto.setUserId(userId);
            return new ResponseEntity<>(expenseService.createExpense(expenseDto), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Boolean> checkHealth(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


}
