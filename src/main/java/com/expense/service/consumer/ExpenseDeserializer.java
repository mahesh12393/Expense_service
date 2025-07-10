package com.expense.service.consumer;

import com.expense.service.dto.ExpenseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class ExpenseDeserializer implements Deserializer<ExpenseDto> {

    @Override
    public ExpenseDto deserialize(String arg0, byte[] arg1){
        ObjectMapper objectMapper = new ObjectMapper();
        ExpenseDto expenseDto = null;

        try{
            expenseDto = objectMapper.readValue(arg1, ExpenseDto.class);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return expenseDto;
    }
}
