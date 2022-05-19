package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.TimeKeeping;
import com.example.demo.model.Wage;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.TimeKeepingRepository;
import com.example.demo.repository.WageRepository;

@Service
public class TimeKeepingService {
	@Autowired
	private TimeKeepingRepository timeKeepingRepository;
	@Autowired
	private WageRepository wageRepository;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WageService wageService;
	public boolean checkTimeKeepingExisted(Employee employee) {
		Optional<TimeKeeping> optional = timeKeepingRepository.findByDateAndEmployeeId(getStringCurrentDate(), employee.getId());
		if(optional.isPresent()) return true;
		else return false;
	}
	public void createNewTimeKeeping(Employee employee) {
		TimeKeeping timeKeeping = new TimeKeeping();
		Wage wage = new Wage();
		Optional<Wage> wageOptional = wageRepository.findWageByMonthAndEmployeeId(LocalDate.now().getMonthValue(), employee.getId());
		if(wageOptional.isPresent()) wage = wageOptional.get();
		timeKeeping.setEmployee(employee);
		timeKeeping.setWage(wage);
		timeKeepingRepository.save(timeKeeping);
		
	}
	public TimeKeeping getByDateAndEmployeeId(Employee employee) {
		Optional<TimeKeeping> optional = timeKeepingRepository.findByDateAndEmployeeId(getStringCurrentDate(), employee.getId());
		if(optional.isPresent()) return optional.get();
		else return null;
	}
	public String getStringCurrentDate() {
		LocalDate currentDate = LocalDate.now();
		Date date = Date.from(currentDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	public TimeKeeping getById(Integer id) {
		Optional<TimeKeeping> optional = timeKeepingRepository.findById(id);
		if(optional.isPresent()) return optional.get();
		else return null;
	}
	public void updateTimeKeeping(TimeKeeping timeKeeping,Integer previousWork) {
		timeKeepingRepository.save(timeKeeping);
		Integer diff = timeKeeping.getWork() - previousWork;
		Employee employee = employeeService.getEmployeeById(timeKeeping.getEmployee().getId());
		Long currentWage = employee.getWage();
		wageService.updateWorkAndTotalWage(employee, diff);
		
	}
}
