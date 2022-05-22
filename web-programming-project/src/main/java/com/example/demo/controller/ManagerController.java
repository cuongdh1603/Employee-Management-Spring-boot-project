package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.TimeKeepingDto;
import com.example.demo.model.Employee;
import com.example.demo.model.TimeKeeping;
import com.example.demo.model.Wage;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.TimeKeepingService;
import com.example.demo.service.WageService;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Controller
@Secured("ROLE_MA")
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WageService wageService;
	@Autowired
	private TimeKeepingService timeKeepingService;
	@GetMapping()
	public String index() {
		return "manager/index";
	}
	@GetMapping("/employees")
	public String listEmployee(Model model) {
		//demo department with id = 1
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		Employee employee = employeeService.getEmployeeByUsername(userDetails.getUsername());
		log.info("Username: "+userDetails.getUsername()+" Logged user ID : "+employee.getDepartment().getId());
		List<Employee> employees = employeeService.getEmployeesToManagerByDepartmentId(employee.getDepartment().getId());
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
			@RequestParam("newWage") String strNewWage,
			RedirectAttributes ra
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
		
		return "redirect:/manager/wage";
	}
	@GetMapping("/timekeeping")
	public String timekeeping(Model model) {
		Date date = new Date();
		model.addAttribute("currentDate", date);
		List<Employee> employees = employeeService.getEmployeesToManagerByDepartmentId(1);
		TimeKeepingDto timeKeepingDto = new TimeKeepingDto();
		for (Employee e : employees) {
			if(!wageService.checkWageExisted(e)) {
				log.info("Create wage");
				wageService.createNewWage(e);
			}
			if(!timeKeepingService.checkTimeKeepingExisted(e)) {
				log.info("Create timekeeping");
				timeKeepingService.createNewTimeKeeping(e);
			}
			timeKeepingDto.addTimeKeeping(timeKeepingService.getByDateAndEmployeeId(e));
		}
		log.info("Size of Dto : "+timeKeepingDto.getTimeKeepings().size());
		model.addAttribute("form", timeKeepingDto);
		return "manager/timekeeping";
	}
	@PostMapping("/save_timekeeping")
	public String saveTimeKeeping(Model model,
			@ModelAttribute("form") TimeKeepingDto timeKeepingDto,
			RedirectAttributes ra) {
		for (TimeKeeping tk : timeKeepingDto.getTimeKeepings()) {
			TimeKeeping timeKeeping = timeKeepingService.getById(tk.getId());
			Integer previousWork = timeKeeping.getWork();
			timeKeeping.setWork(tk.getWork());
			timeKeepingService.updateTimeKeeping(timeKeeping, previousWork);
		}
		ra.addFlashAttribute("updateSuccess", "Đã lưu thành công vào cơ sở dữ liệu");
		return "redirect:/manager/timekeeping";
	}
	@GetMapping("/report")
	public String currentReport(Model model) {
		LocalDate currentDate = LocalDate.now();
		//Date date = new Date();
		model.addAttribute("currentDate", currentDate);
		List<TimeKeeping> timeKeepings = timeKeepingService.getTimeKeepingsByMonthAndYear(currentDate.getMonthValue(), currentDate.getYear(), 3);//value 3 set default to test a manager
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
		return "manager/report";
	}
	@PostMapping("/show_report")
	public String showReport(Model model,
			@RequestParam("datepicker") String strDate) {
		log.info("StrDate: "+strDate);
		//Date searchDate = new Date();
		if(!checkValidStringTime(strDate)) {
			LocalDate currentDate = LocalDate.now();
			List<TimeKeeping> timeKeepings = timeKeepingService.getTimeKeepingsByMonthAndYear(currentDate.getMonthValue(), currentDate.getYear(), 3);//value 3 set default to test a manager
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
			List<TimeKeeping> timeKeepings = timeKeepingService.getTimeKeepingsByMonthAndYear(date.getMonthValue(), date.getYear(), 3);//value 3 set default to test a manager
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
		return "manager/report";
	}
	@GetMapping("/update_password")
	public String updatePassword() {
		log.info("Password update manager");
		return "manager/change_password";
	}
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "manager/accessDenied";
	}
	public Long extractNumberFromString(String s) {
		Pattern pattern = Pattern.compile("[^0-9]");
		String numberOnly = pattern.matcher(s).replaceAll("");
		return Long.valueOf(numberOnly);
	}
	public static void main(String[] args) {
//		if(checkValidStringTime("MM-yyyy")) {
//			System.out.println("Valid");
//		}
//		else System.out.println("Invalid");
		System.out.println(countWeekendDays(5, 2022) + "  " + getDaysOfMonth(5, 2022));
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
	public static int countWeekendDays(int month, int year) {
	    Calendar calendar = Calendar.getInstance();
	    // Note that month is 0-based in calendar, bizarrely.
	    calendar.set(year, month - 1, 1);
	    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

	    int count = 0;
	    for (int day = 1; day <= daysInMonth; day++) {
	        calendar.set(year, month - 1, day);
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
	            count++;
	        }
	    }
	    return count;
	}
	public static Integer getDaysOfMonth(int month, int year) {
		return YearMonth.of(year, month).lengthOfMonth();
	}
}
