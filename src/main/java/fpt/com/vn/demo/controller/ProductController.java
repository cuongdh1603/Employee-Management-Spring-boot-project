package fpt.com.vn.demo.controller;

import fpt.com.vn.demo.model.Product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @GetMapping("/")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "demo-content/home";
    }
    @GetMapping("/department")
    public String department(){
        return "demo-content/department";
    }
    @GetMapping("/position")
    public String position(){
        return "demo-content/position";
    }
    @GetMapping("/employee")
    public String employee(){
        return "demo-content/employee";
    }
    @GetMapping("/document")
    public String document(){
        return "demo-content/document";
    }
}
