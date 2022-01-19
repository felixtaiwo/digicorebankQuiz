package com.digicore.assessment.bank.application.security;

import com.digicore.assessment.bank.application.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailService implements UserDetailsService {
    @Autowired
   private BankService service;
    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        if(service.find(accountNumber)!=null){
            return (UserDetails) service.find(accountNumber);
        }
      throw new UsernameNotFoundException("Account Number Not found");
    }
}
