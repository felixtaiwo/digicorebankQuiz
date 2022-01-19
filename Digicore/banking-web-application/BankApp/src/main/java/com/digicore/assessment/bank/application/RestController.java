package com.digicore.assessment.bank.application;

import com.digicore.assessment.bank.application.security.JWTTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin
@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private BankService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @PostMapping(value = "/create_account")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> account) {
        if (Double.parseDouble(account.get("initialDeposit")) >= 500 && Utilities.validateAccountName((String) account.get("accountName"))) {
            Account account1 = new Account();
            Map<String, Object> result = new HashMap<>();
            account1.setAccountNumber(Utilities.generateAccountNumber());
            account1.setAccountpassword((String) account.get("accountPassword"));
            account1.setBalance(Double.parseDouble(account.get("initialDeposit")));
            account1.setAccountName((String) account.get("accountName"));
            service.save(account1);
            result.put("responseCode", 200);
            result.put("status", "success");
            result.put("message", "Account No- " + account1.getAccountNumber());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account Name exits or/and invalid opening deposit");

    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        final Account account = service.find(credentials.get("accountNumber"));
        if(account!=null && new BCryptPasswordEncoder().matches(credentials.get("accountPassword"),account.getAccountpassword())){
            jwtTokenUtil.generateToken(account);
            return ResponseEntity.status(HttpStatus.OK).body(jwtTokenUtil.generateToken(account));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(value = "/withdrawal")
    public ResponseEntity<?> withdraw(@RequestBody Map<String, String> withdrawal) {
        Account account = service.find(withdrawal.get("accountNumber"));
        if (account != null) {
            Map<String, Object> result = new HashMap<>();
            if ((account.getBalance() - Double.parseDouble(withdrawal.get("withdrawnAmount"))) >= 500 && new BCryptPasswordEncoder().matches(withdrawal.get("accountPassword"),account.getAccountpassword())) {
                Transaction transaction = new Transaction();
                transaction.setTransactionType(Transaction.TransactionTypes.WITHDRAWAL);
                transaction.setTransactionDate(new Date());
                transaction.setAmount(Double.parseDouble(withdrawal.get("withdrawnAmount")));
                transaction.setNarration("Withdrawal");
                transaction.setAccountBalance(account.getBalance() - Double.parseDouble(withdrawal.get("withdrawnAmount")));
                List<Transaction> transactions = account.getTransactions() != null ? account.getTransactions() : new ArrayList<>();
                transactions.add(transaction);
                account.setTransactions(transactions);
                account.setBalance(transaction.getAccountBalance());
                service.save(account);
                result.put("responseCode", 200);
                result.put("status", "success");
                result.put("message", "Withdraw successful");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                result.put("responseCode", 400);
                result.put("status", "error");
                result.put("message", account.getBalance() - Double.parseDouble(withdrawal.get("withdrawnAmount")) >= 500 ? "Wrong credentials" : "Insufficient Balance");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, String> deposit) {
        Account account = service.find(deposit.get("accountNumber"));
        if (account != null) {
            Map<String, Object> result = new HashMap<>();
            if (Double.parseDouble(deposit.get("amount")) <= 1000000 && Double.parseDouble(deposit.get("amount")) > 1) {
                Transaction transaction = new Transaction();
                transaction.setTransactionType(Transaction.TransactionTypes.DEPOSIT);
                transaction.setTransactionDate(new Date());
                transaction.setAmount(Double.parseDouble(deposit.get("amount")));
                transaction.setNarration("Deposit");
                transaction.setAccountBalance(account.getBalance() + Double.parseDouble(deposit.get("amount")));
                List<Transaction> transactions = account.getTransactions() != null ? account.getTransactions() : new ArrayList<>();
                transactions.add(transaction);
                account.setTransactions(transactions);
                account.setBalance(transaction.getAccountBalance());
                service.save(account);
                result.put("responseCode", 200);
                result.put("status", "success");
                result.put("message", "Deposit successful");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                result.put("responseCode", 400);
                result.put("status", "error");
                result.put("message", "Invalid Amount");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(value = "/account_statement/{accountNumber}")
    public ResponseEntity<?> accountStatement(@PathVariable("accountNumber") String accountNumber) {
        Account account = service.find(accountNumber);
        if (account != null) {
            return ResponseEntity.status(HttpStatus.OK).body(account.getTransactions()!=null?account.getTransactions():new ArrayList<>());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping(value = "/account_info/{accountNumber}")
    public ResponseEntity<?> accountInfo(@PathVariable("accountNumber") String accountNumber) {
        Account account = service.find(accountNumber);
        Map<String, Object> result = new HashMap<>();
        if (account != null) {
            Map<String, Object> account_info = new HashMap<>();
            account_info.put("accountName",account.getAccountName());
            account_info.put("accountNumber",account.getAccountNumber());
            account_info.put("balance",account.getBalance());
                result.put("account",account_info);
                result.put("responseCode", 200);
                result.put("status", "success");
                result.put("message", "account info fetch successful");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                result.put("responseCode", 400);
                result.put("status", "error");
                result.put("message", "Invalid Amount");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

}
