package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.TimeKeepingDto;
import com.example.demo.model.Employee;
import com.example.demo.model.TimeKeeping;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.TimeKeepingService;
import com.example.demo.service.WageService;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WageService wageService;
	@Autowired
	private TimeKeepingService timeKeepingService;
	@GetMapping()
	public String index() {
		return "admin/index";
	}
	
	@GetMapping("/wage")
	public String wage(Model model) {
		List<Employee> managers = employeeService.getListManager();
		model.addAttribute("employees", managers);
		return "admin/update_wage";
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
		return "redirect:/admin/wage";
	}
	@GetMapping("/timekeeping")
	public String timekeeping(Model model) {
		Date date = new Date();
		model.addAttribute("currentDate", date);
		List<Employee> managers = employeeService.getListManager();
		//List<TimeKeeping> timeKeepings = new ArrayList<TimeKeeping>();
		TimeKeepingDto timeKeepingDto = new TimeKeepingDto();
		//check if wage table of each manager has been existed or not.
		for (Employee e : managers) {
			if(!wageService.checkWageExisted(e)) {
				log.info("Create wage");
				wageService.createNewWage(e);
			}
			if(!timeKeepingService.checkTimeKeepingExisted(e)) {
				log.info("Create timekeeping");
				timeKeepingService.createNewTimeKeeping(e);
			}
			//timeKeepings.add(timeKeepingService.getByDateAndEmployeeId(e));
			timeKeepingDto.addTimeKeeping(timeKeepingService.getByDateAndEmployeeId(e));
		}
		log.info("Size of Dto : "+timeKeepingDto.getTimeKeepings().size());
		model.addAttribute("form", timeKeepingDto);
		return "admin/timekeeping";
	}
	@PostMapping("/save_timekeeping")
	public String saveTimekeeping(Model model,
			@ModelAttribute("form") TimeKeepingDto timeKeepingDto) {
		for (TimeKeeping tk : timeKeepingDto.getTimeKeepings()) {
			TimeKeeping timeKeeping = timeKeepingService.getById(tk.getId());
			Integer previousWork = timeKeeping.getWork();
			timeKeeping.setWork(tk.getWork());
			timeKeepingService.updateTimeKeeping(timeKeeping,previousWork);
			
		}
		return "redirect:/admin";
	}
	@GetMapping("/update_password")
	public String updatePassword() {
		log.info("Password update 12345");
		return "admin/change_password";
	}
	public Long extractNumberFromString(String s) {
		Pattern pattern = Pattern.compile("[^0-9]");
		String numberOnly = pattern.matcher(s).replaceAll("");
		return Long.valueOf(numberOnly);
	}
}
