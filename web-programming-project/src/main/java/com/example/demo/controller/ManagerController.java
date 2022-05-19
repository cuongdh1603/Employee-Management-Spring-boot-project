package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private EmployeeService employeeService;
	@GetMapping()
	public String index() {
		return "manager/index";
	}
	@GetMapping("/employees")
	public String listEmployee(Model model) {
		//demo department with id = 1
		List<Employee> employees = employeeService.getEmployeesToManagerByDepartmentId(1);
		log.info(employees.size());
		model.addAttribute("employees", employees);
		return "manager/list_employee";
	}
	@GetMapping("/wage")
	public String salary(Model model) {
		List<Employee> employees = employeeService.getEmployeesToManagerByDepartmentId(1);
		model.addAttribute("employees", employees);
		return "manager/update_wage";
	}
	@PostMapping("/save_wage/{id}")
	public String saveWage(Model model,
			@PathVariable("id") Integer id,
			@RequestParam("newWage") String strNewWage) {
		Long newWage = extractNumberFromString(strNewWage);
		Employee employee = employeeService.getEmployeeById(id);
		boolean updateSuccess = employeeService.updateWage(employee, newWage);
		if(updateSuccess) {
			log.info("Successfully");
		}
		else {
			log.info("Failed");
		}
		return "redirect:/manager/wage";
	}
	@GetMapping("/timekeeping")
	public String timekeeping(Model model) {
		Date date = new Date();
		model.addAttribute("currentDate", date);
		return "manager/timekeeping";
	}
	@GetMapping("/update_password")
	public String updatePassword() {
		log.info("Password update manager");
		return "manager/change_password";
	}
	public Long extractNumberFromString(String s) {
		Pattern pattern = Pattern.compile("[^0-9]");
		String numberOnly = pattern.matcher(s).replaceAll("");
		return Long.valueOf(numberOnly);
	}
}
