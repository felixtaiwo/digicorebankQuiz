package com.digicore.assessment.bank.application;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
     enum TransactionTypes {
        DEPOSIT,WITHDRAWAL
    }
    private int id;
    private Date transactionDate;
    private TransactionTypes transactionType;
    private String narration;
    private double amount;
    private double accountBalance;


}
