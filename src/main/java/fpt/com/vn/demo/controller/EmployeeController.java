package fpt.com.vn.demo.controller;

import fpt.com.vn.demo.service.EmployeeService;
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
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/employee/insert")
    public ResponseEntity<Object> insertEmployee(@RequestParam("de") String department,@RequestParam("po") String position){
        employeeService.insertEmployee(department,position);
        return new ResponseEntity<Object>("Insert employee successfully", HttpStatus.OK);
    }
}
