package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public Employee getEmployeeById(Integer id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		if(optional.isPresent()) 
			return optional.get();
		else return null;
	}
	public List<Employee> getEmployeesByDepartmentId(Integer id){
		return employeeRepository.findByDepartmentId(id);
	}
	public List<Employee> getListEmployee(){
		return employeeRepository.findActiveEmployee();
	}
	public void saveNewEmployee(Employee employee) {
		//System.out.println("Save moi");
		employeeRepository.save(employee);
	}
	public Integer getMaxId(Integer id) {
		return employeeRepository.findMaxId(id);
	}
	public boolean checkPositionIsOccupied(Employee newEmployee) {
		if(newEmployee.getPosition().getId()==2) return false;
		Employee employee = employeeRepository.findByDepartmentAndPosition(newEmployee.getDepartment().getId(),newEmployee.getPosition().getId());
		if(employee == null) return false;
		else return true;
	}
	public boolean checkUpdatedPositionIsOccupied(Employee updatedEmployee) {
		if(updatedEmployee.getPosition().getId()==2) return false;
		Employee employee = employeeRepository.findByDepartmentAndPosition(updatedEmployee.getDepartment().getId(),updatedEmployee.getPosition().getId());
		if(employee == null || employee.getId() == updatedEmployee.getId()) return false;
		else return true;
	}
	public void saveUpdateEmployee(Employee employee,Employee updateEmployee) {
		if(employee != null) {
			employee.setPhoto(updateEmployee.getPhoto());
			employee.setEmail(updateEmployee.getEmail());
			employee.setPhoneNumber(updateEmployee.getPhoneNumber());
			employee.setAddress(updateEmployee.getAddress());
			employee.setDescription(updateEmployee.getDescription());
			employee.setDepartment(updateEmployee.getDepartment());
			employee.setPosition(updateEmployee.getPosition());
			if(employee.getAccount() != null) {
				Account account = userService.getById(employee.getAccount().getId());
				if(updateEmployee.getPosition().getId()==1) {
					Role role = roleRepository.findRoleById(2);//role manager
					account.setRole(role);
				}
				else {
					Role role = roleRepository.findRoleById(3);//role employee
					account.setRole(role);
				}
				accountRepository.save(account);
				employee.setAccount(account);
			}
			employeeRepository.save(employee);
		}
	}
	public void inactiveEmployee(Employee employee) {
		if(employee != null) {
			employee.setIsActive(0);
			employeeRepository.save(employee);
		}
	}
	public List<Employee> getEmployeesToManagerByDepartmentId(Integer id){
		return employeeRepository.findEmployeesToManagerByDepartmentId(id);
	}
	public boolean updateWage(Employee employee,Long newWage) {
		if(employee != null) {
			employee.setWage(newWage);
			employeeRepository.save(employee);
			return true;
		}
		return false;
	}
	public List<Employee> getListManager(){
		return employeeRepository.findManagers();
	}
	public Employee getEmployeeByUsername(String username) {
		return employeeRepository.findEmployeeByUsername(username);
	}
	public void updateAccountToEmployee(Employee employee, Account newAccount) {
		Account account = employee.getAccount();
		//In case this account is not exist
		if(account == null) {
			log.info("Exist");
			Role role = new Role();
			if(employee.getPosition().getId()==1) {
				role = roleRepository.findRoleById(2);
				
			}
			else {
				role = roleRepository.findRoleById(3);
			}
			newAccount.setPassword(bCryptPasswordEncoder.encode(newAccount.getPassword()));
			newAccount.setRole(role);
			accountRepository.save(newAccount);
		}
		else {//In case this account is exist
			account.setUsername(newAccount.getUsername());
			account.setPassword(bCryptPasswordEncoder.encode(newAccount.getPassword()));
			accountRepository.save(account);
		}
		account = userService.findAccountByUsername(newAccount.getUsername());
		employee.setAccount(account);
		employeeRepository.save(employee);
	}
}
