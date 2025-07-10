package com.expense.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {

    private String externalId;

    @NonNull
    @JsonProperty(value = "amount")
    private String amount;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "merchant")
    private String merchant;

    @JsonProperty(value = "currency")
    private String currency;

    @JsonProperty(value = "created_at")
    private String createdAt;

    public ExpenseDto(String json){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            ExpenseDto expenseDto = objectMapper.readValue(json, ExpenseDto.class);
            this.externalId = expenseDto.getExternalId();
            this.amount = expenseDto.getAmount();
            this.userId = expenseDto.getUserId();
            this.merchant = expenseDto.getMerchant();
            this.currency = expenseDto.getCurrency();
            this.createdAt = expenseDto.getCreatedAt();
        } catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Failed to deserialize");
        }
    }
}
