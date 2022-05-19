package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;

@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	public List<Department> getAllDepartment(){
		return departmentRepository.findAll();
	}
	public Department getDepartmentById(Integer id) {
		Optional<Department> options = departmentRepository.findById(id);
		if(options.isPresent()) {
			return options.get();
		}
		return null;
	}
	public List<Department> getOtherDepartments(Integer id) {
		return departmentRepository.findOtherDepartments(id);
	}
}
