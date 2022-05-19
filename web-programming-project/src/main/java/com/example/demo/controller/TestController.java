package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DepartmentService;
import com.example.demo.service.PositionService;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PositionService positionService;
	
	@GetMapping("/department")
	public ResponseEntity<Object> getAllDepartment(){
		return new ResponseEntity<Object>(departmentService.getAllDepartment(),HttpStatus.OK);
	}
	@GetMapping("/position")
	public ResponseEntity<Object> getAllPosition(){
		return new ResponseEntity<Object>(positionService.getAllPositions(),HttpStatus.OK);
	}
}
