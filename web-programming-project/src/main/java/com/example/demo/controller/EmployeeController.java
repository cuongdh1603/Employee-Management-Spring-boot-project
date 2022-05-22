package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.model.TimeKeeping;
import com.example.demo.model.Wage;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.TimeKeepingService;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Controller
@Secured("ROLE_EM")
@RequestMapping("/employ")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TimeKeepingService timeKeepingService;
	@GetMapping()
	public String index() {
		return "employee/index";
	}
	@GetMapping("/employees")
	public String listEmployee(Model model) {
		//demo department with id = 1
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Employee employee = employeeService.getEmployeeByUsername(userDetails.getUsername());
		log.info("Username: "+userDetails.getUsername()+" Logged user ID : "+employee.getDepartment().getId());
		List<Employee> employees = employeeService.getEmployeesToManagerByDepartmentId(employee.getDepartment().getId());
		//log.info(employees.size());
		model.addAttribute("employees", employees);
		return "employee/list_employee";
	}
	@GetMapping("/report")
	public String currentReport(Model model) {
		LocalDate currentDate = LocalDate.now();
		model.addAttribute("currentDate", currentDate);
		List<TimeKeeping> timeKeepings = timeKeepingService.getTimeKeepingsByMonthAndYear(currentDate.getMonthValue(), currentDate.getYear(), 1);//value 1 set default to test a employee
		if(timeKeepings.isEmpty()) {
			model.addAttribute("work", "Không có dữ liệu");
			model.addAttribute("rest", "Không có dữ liệu");
			model.addAttribute("totalWage", "Không có dữ liệu");
		}
		else {
			Wage wage = timeKeepings.get(0).getWage();
			Integer rest = getDaysOfMonth(wage.getMonth(), wage.getYear()) - wage.getWork();
			model.addAttribute("work", wage.getWork());
			model.addAttribute("rest", rest);
			model.addAttribute("totalWage", wage.getFormatTotalWage());
		}
		model.addAttribute("timeKeepings", timeKeepings);
		return "employee/report";
	}
	@PostMapping("/show_report")
	public String showReport(Model model,
			@RequestParam("datepicker") String strDate) {
		log.info("StrDate: "+strDate);
		//Date searchDate = new Date();
		if(!checkValidStringTime(strDate)) {
			LocalDate currentDate = LocalDate.now();
			List<TimeKeeping> timeKeepings = timeKeepingService.getTimeKeepingsByMonthAndYear(currentDate.getMonthValue(), currentDate.getYear(), 3);//value 3 set default to test an employee
			model.addAttribute("errorTimeInput", "Định dạng thời gian không phù hợp (MM-yyyy). Hãy nhập lại");
			model.addAttribute("timeKeepings", timeKeepings);
			model.addAttribute("currentDate", currentDate);
			if(timeKeepings.isEmpty()) {
				model.addAttribute("work", "Không có dữ liệu");
				model.addAttribute("rest", "Không có dữ liệu");
				model.addAttribute("totalWage", "Không có dữ liệu");
			}
			else {
				Wage wage = timeKeepings.get(0).getWage();
				Integer rest = getDaysOfMonth(wage.getMonth(), wage.getYear()) - wage.getWork();
				model.addAttribute("work", wage.getWork());
				model.addAttribute("rest", rest);
				model.addAttribute("totalWage", wage.getFormatTotalWage());
			}
		}
		else {
			LocalDate date = getFormatDate(strDate);
			List<TimeKeeping> timeKeepings = timeKeepingService.getTimeKeepingsByMonthAndYear(date.getMonthValue(), date.getYear(), 1);//value 1 set default to test an employee
			model.addAttribute("timeKeepings", timeKeepings);
			model.addAttribute("currentDate", date);
			if(timeKeepings.isEmpty()) {
				model.addAttribute("work", "Không có dữ liệu");
				model.addAttribute("rest", "Không có dữ liệu");
				model.addAttribute("totalWage", "Không có dữ liệu");
			}
			else {
				Wage wage = timeKeepings.get(0).getWage();
				Integer rest = getDaysOfMonth(wage.getMonth(), wage.getYear()) - wage.getWork();
				model.addAttribute("work", wage.getWork());
				model.addAttribute("rest", rest);
				model.addAttribute("totalWage", wage.getFormatTotalWage());
			}
		}
		return "employee/report";
	}
	@GetMapping("/update_password")
	public String updatePassword() {
		log.info("Password update employee");
		return "employee/change_password";
	}
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "employee/accessDenied";
	}
	public static boolean checkValidStringTime(String time) {
		String pattern = "MM-yyyy";
		SimpleDateFormat form = new SimpleDateFormat(pattern);
		try {
			Date date = form.parse(time);
			return true;
		} catch (ParseException e) {
			return false;
		}
		
	}
	public LocalDate getFormatDate(String time) {
		String pattern = "MM-yyyy";
		SimpleDateFormat form = new SimpleDateFormat(pattern);
		try {
			Date date = form.parse(time);
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			return localDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public static Integer getDaysOfMonth(int month, int year) {
		return YearMonth.of(year, month).lengthOfMonth();
	}
}
