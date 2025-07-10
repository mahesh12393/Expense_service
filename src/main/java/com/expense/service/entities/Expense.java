package com.expense.service.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Expense {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "amount")
    private String amount;

    @Column(name = "merchant")
    private String merchant;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    @PreUpdate
    private void prepareEntity(){
        if(this.externalId == null) this.externalId = UUID.randomUUID().toString();
        if(this.createdAt == null) this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
