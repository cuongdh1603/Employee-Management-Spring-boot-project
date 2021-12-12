package fpt.com.vn.demo.controller;

import fpt.com.vn.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping("/account/insert")
    public ResponseEntity<Object> insertAccount(@RequestParam("un") String username,@RequestParam("pw") String password){
        accountService.insertAccount(username, password);
        return new ResponseEntity<Object>("Insert account successfully", HttpStatus.OK);
    }
}
