package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.Wage;
import com.example.demo.repository.WageRepository;

@Service
public class WageService {
	@Autowired
	private WageRepository wageRepository;
	public boolean checkWageExisted(Employee employee) {
		LocalDate currentDate = LocalDate.now();
		Optional<Wage> optional = wageRepository.findWageByMonthAndEmployeeId(currentDate.getMonthValue(), employee.getId());
		if(optional.isPresent()) return true;
		else return false;
	}
	public Wage createNewWage(Employee employee) {
		Wage wage = new Wage();
		wage.setEmployee(employee);
		wageRepository.save(wage);
		LocalDate currentDate = LocalDate.now();
		Optional<Wage> optional = wageRepository.findWageByMonthAndEmployeeId(currentDate.getMonthValue(), employee.getId());
		if(optional.isPresent()) return optional.get();
		else return null;
	}
	public Wage getWageByTimeAndEmployeeId(Employee employee) {
		LocalDate currentDate = LocalDate.now();
		Optional<Wage> optional = wageRepository.findWageByTimeAndEmployeeId(currentDate.getMonthValue(),currentDate.getYear(), employee.getId());
		if(optional.isPresent()) return optional.get();
		else return null;
	}
	public void updateWorkAndTotalWage(Employee employee,Integer extraWork) {
		Wage wage = getWageByTimeAndEmployeeId(employee);
		if(wage != null) {
			wage.setWork(wage.getWork() + extraWork);
			wage.setTotal(wage.getTotal() + extraWork*employee.getWage());
			wageRepository.save(wage);
		}
	}
	public static void main(String[] args) {
		LocalDate currentDate = LocalDate.now();
		System.out.println(currentDate.getMonthValue()+" "+currentDate.getDayOfMonth());
		Date date = Date.from(currentDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = format.format(date);
		System.out.println(strDate);
	}
}
