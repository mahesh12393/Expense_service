package com.expense.service.services;

import com.expense.service.dto.ExpenseDto;
import com.expense.service.entities.Expense;
import com.expense.service.repository.ExpenseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    private ObjectMapper objectMapper;

    @Autowired
    ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public boolean createExpense(ExpenseDto expenseDto){
        setCurrency(expenseDto);
        try{
            expenseRepository.save(objectMapper.convertValue(expenseDto,Expense.class));
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean updateExpense(ExpenseDto expenseDto){
        Optional<Expense> expenseFound = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(),expenseDto.getExternalId());
        if(expenseFound.isEmpty()){
            return false;
        }
        Expense expense = expenseFound.get();
        expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency())? expenseDto.getCurrency(): expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant())? expenseDto.getMerchant() : expense.getMerchant());
        expense.setAmount(expenseDto.getAmount());
        expenseRepository.save(expense);
        return  true;
    }


    public List<ExpenseDto> getExpenses(String userId){
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return objectMapper.convertValue(expenses, new TypeReference<List<ExpenseDto>>() {});
    }

    private void setCurrency(ExpenseDto expenseDto){
        if(Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("INR");
        }
    }
}
