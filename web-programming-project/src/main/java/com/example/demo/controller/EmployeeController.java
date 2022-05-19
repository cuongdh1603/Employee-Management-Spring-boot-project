package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Controller
@RequestMapping("/employ")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@GetMapping()
	public String index() {
		return "employee/index";
	}
	@GetMapping("/employees")
	public String listEmployee(Model model) {
		//demo department with id = 1
		List<Employee> employees = employeeService.getEmployeesToManagerByDepartmentId(1);
		log.info(employees.size());
		model.addAttribute("employees", employees);
		return "employee/list_employee";
	}
	@GetMapping("/update_password")
	public String updatePassword() {
		log.info("Password update employee");
		return "employee/change_password";
	}
}
