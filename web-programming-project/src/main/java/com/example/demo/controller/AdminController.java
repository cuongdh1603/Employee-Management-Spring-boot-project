package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.PasswordDto;
import com.example.demo.dto.TimeKeepingDto;
import com.example.demo.model.Account;
import com.example.demo.model.Employee;
import com.example.demo.model.TimeKeeping;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.TimeKeepingService;
import com.example.demo.service.UserService;
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
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Secured("ROLE_AD")
	@GetMapping()
	public String index() {
		return "admin/index";
	}
	@Secured("ROLE_AD")
	@GetMapping("/wage")
	public String wage(Model model) {
		List<Employee> managers = employeeService.getListManager();
		model.addAttribute("employees", managers);
		return "admin/update_wage";
	}
	@Secured("ROLE_AD")
	@PostMapping("/save_wage/{id}")
	public String saveWage(Model model,
			@PathVariable("id") Integer id,
			@RequestParam("newWage") String strNewWage,
			RedirectAttributes ra//pass attribute to redirect
			) {
		Long newWage = extractNumberFromString(strNewWage);
		Employee employee = employeeService.getEmployeeById(id);
		boolean updateSuccess = employeeService.updateWage(employee, newWage);
		if(updateSuccess) {
			log.info("Successfully");
			ra.addFlashAttribute("updateSuccess", "Cập nhật lương thành công");
		}
		else {
			log.info("Failed");
		}
		
		return "redirect:/admin/wage";
	}
	@Secured("ROLE_AD")
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
	@Secured("ROLE_AD")
	@PostMapping("/save_timekeeping")
	public String saveTimekeeping(Model model,
			@ModelAttribute("form") TimeKeepingDto timeKeepingDto,
			RedirectAttributes ra) {
		for (TimeKeeping tk : timeKeepingDto.getTimeKeepings()) {
			TimeKeeping timeKeeping = timeKeepingService.getById(tk.getId());
			Integer previousWork = timeKeeping.getWork();
			timeKeeping.setWork(tk.getWork());
			timeKeepingService.updateTimeKeeping(timeKeeping,previousWork);
			
		}
		ra.addFlashAttribute("updateSuccess", "Đã lưu thành công vào cơ sở dữ liệu");
		return "redirect:/admin/timekeeping";
	}
	@Secured("ROLE_AD")
	@GetMapping("/update_password")
	public String updatePassword() {
		log.info("Password update 12345");
		return "admin/change_password";
	}
	@Secured("ROLE_AD")
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "admin/accessDenied";
	}
	@Secured("ROLE_AD")
	@GetMapping("/updated_password")
	public String updatePassword(Model model) {
		log.info("Password update employee");
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		PasswordDto passwordDto = new PasswordDto();
		model.addAttribute("passDto", passwordDto);
		return "admin/change_password";
	}
	@Secured("ROLE_AD")
	@PostMapping("/saved_password")
	public String savePassword(Model model,
			@Valid @ModelAttribute("passDto") PasswordDto passwordDto,
			BindingResult result,
			@RequestParam("rePassword") String rePassword,
			RedirectAttributes ra) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		if(result.hasErrors()) {
			model.addAttribute("passDto", passwordDto);
			return "admin/change_password";
		}
		if(!passwordDto.getNewPassword().equals(rePassword)) {
			model.addAttribute("passDto", passwordDto);
			model.addAttribute("errorRePassword", "Mật khẩu mới không khớp");
			return "admin/change_password";
		}
		if(!bCryptPasswordEncoder.matches(passwordDto.getPassword(),userService.findAccountByUsername(userDetails.getUsername()).getPassword())) {
			result.addError(new FieldError("passDto","password","Mật khẩu sai"));
            return "admin/change_password";
		}
		Account account = new Account();
		account.setUsername(userDetails.getUsername());
		account.setPassword(passwordDto.getNewPassword());
		userService.updatePassword(account);
		log.info("Password: " + passwordDto.getNewPassword());
		ra.addFlashAttribute("updateSuccess", "Thay đổi mật khẩu thành công");
		return "redirect:/admin/updated_password";
	}
	@GetMapping("/infor")
	public String personalInfor(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Employee employee = employeeService.getEmployeeByUsername(userDetails.getUsername());
		model.addAttribute("employee", employee);
		return "admin/personal_infor";
	}
	public Long extractNumberFromString(String s) {
		Pattern pattern = Pattern.compile("[^0-9]");
		String numberOnly = pattern.matcher(s).replaceAll("");
		return Long.valueOf(numberOnly);
	}
}
