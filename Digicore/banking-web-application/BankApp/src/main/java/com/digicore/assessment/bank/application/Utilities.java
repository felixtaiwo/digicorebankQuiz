package com.digicore.assessment.bank.application;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Utilities {
    private static final String[] digits= {"0","1","2","3","4","5","6","7","8","9"};
    public static boolean validateAccountName(String accountName){
        for (Map.Entry pair : BankService.getAccounts().entrySet()) {
            if (BankService.getAccounts().get(pair.getKey()).getAccountName().equalsIgnoreCase(accountName)) {
                return false;
            }
        }
        return true;
    }
    public static String generateAccountNumber(){
        Random rand = new Random();
        boolean status = false;
        String accountNumber ="";
        while(!status){
             accountNumber ="";
            for(int i=0; i<10;i++){
                accountNumber= accountNumber.concat(digits[rand.nextInt(10)]);
            }
            status = validateAccountNumber(accountNumber);
        }

        return accountNumber;
    }
    private static boolean validateAccountNumber(String accountNumber){
        return BankService.getAccounts().get(accountNumber) == null;
    }
}
