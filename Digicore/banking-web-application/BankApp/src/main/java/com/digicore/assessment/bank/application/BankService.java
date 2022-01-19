package com.digicore.assessment.bank.application;

import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class BankService {
    static Map<String,Account> accounts;
    public static void initiateAccounts(){
        accounts=new HashMap<>();
    }
    public static Map<String,Account> getAccounts(){
        return accounts;
    }
    public void save(Account account){
        accounts.put(account.getAccountNumber(),account);
    }
    public Account find (String accountNumber){
        return accounts.get(accountNumber);
    }
    public List<Account> findAll(){
        List<Account> all = new ArrayList<>();
        for (Map.Entry pair : accounts.entrySet()) {
            all.add((Account) pair.getValue());
        }
        return all;
    }
    public void delete(String accountNumber){
        accounts.remove(accountNumber);
    }
    public void deleteAll(){
        accounts.clear();
    }
}
