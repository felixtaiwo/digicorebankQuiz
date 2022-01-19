package com.digicore.assessment.bank.application;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Data
public class Account {
    private String accountNumber;
    private String accountName;
    private double balance;
    private List<Transaction> transactions;
    private String accountpassword;

    public void setAccountpassword(String accountpassword) {
       this.accountpassword= new BCryptPasswordEncoder().encode(accountpassword);
    }
}
