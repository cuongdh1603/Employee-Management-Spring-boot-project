package fpt.com.vn.demo.service;

import fpt.com.vn.demo.model.Account;
import fpt.com.vn.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public void insertAccount(String username,String password){
        Account acc = new Account();
        acc.setUsername(username);
        acc.setPassword(password);
        accountRepository.save(acc);
    }
}
