package com.example.demo.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.model.Position;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.PositionService;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Controller
@RequestMapping("/depart")
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PositionService positionService;
	@GetMapping()
	public String departments(Model model) {
		model.addAttribute("departments", departmentService.getAllDepartment());
		return "admin/list_department";
	}
	@GetMapping("/employees/{id}")
	public String listEmployees(Model model,@PathVariable("id") Integer id) {
		Department department = departmentService.getDepartmentById(id);
		List<Employee> employees = employeeService.getEmployeesByDepartmentId(id);
		model.addAttribute("department", department);
		model.addAttribute("employees", employees);
		return "admin/list_employee";
	}
	@GetMapping("/add_emp/{id}")
	public String addEmployees(Model model,@PathVariable("id") Integer id) {
		Department department = departmentService.getDepartmentById(id);
		List<Position> positions = positionService.getAllPositions();
		model.addAttribute("department", department);
		model.addAttribute("positions", positions);
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "admin/add_employ";
	}
	@PostMapping("/save_emp")
	public String saveEmployees(Model model,
			@Valid @ModelAttribute("employee") Employee employee,
			BindingResult result,
			@RequestParam("positions") Integer id_position,
			@RequestParam("id_department") Integer id,
			@RequestParam("image") MultipartFile multipartFile) throws Exception{
		
		Department department = departmentService.getDepartmentById(id);
		if (result.hasErrors()){
			model.addAttribute("department", department);
			model.addAttribute("employee",employee);
            model.addAttribute("positions",positionService.getAllPositions());
            return "admin/add_employ";
        }
		employee.setDepartment(department);
		Position position = positionService.getPositionById(id_position);
		employee.setPosition(position);
		if(employeeService.checkPositionIsOccupied(employee)) {
			model.addAttribute("employee",employee);
			model.addAttribute("department", department);
            model.addAttribute("positions",positionService.getAllPositions());
            model.addAttribute("errorPosition", "Vị trí này hiện tại không còn trống");
            return "admin/add_employ";
		}
		else {
			employee.setStartingWage(employee.getWage());
			log.info("Department ID: " + department.getId());
			Integer codeNum = 0;
			if(employeeService.getMaxId(department.getId())!=null) codeNum = employeeService.getMaxId(department.getId());
			String code = department.getCode() + position.getCode() + String.format("%04d", codeNum + 1);
			employee.setCode(code);
			employee.setIsActive(1);
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			if(fileName.equals("")) employee.setPhoto(null);
			else employee.setPhoto(fileName);
			
			employeeService.saveNewEmployee(employee);
			if(employee.getPhoto()!=null) {
				String uploadDir = "./src/main/resources/static/img/user/" + employee.getCode();
				Path uploadPath = Paths.get(uploadDir);
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				InputStream inputStream = multipartFile.getInputStream();
				Path filePath = uploadPath.resolve(fileName);
				System.out.println(filePath.toFile().getAbsolutePath());
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); 
			}
			return "redirect:/depart";
		}
		
	}
	@GetMapping("/update_emp/{id}")
	public String updateEmployees(Model model,@PathVariable("id") Integer id) {
		Employee employee = employeeService.getEmployeeById(id);
		List<Department> departments = departmentService.getOtherDepartments(employee.getDepartment().getId());
		List<Position> positions = positionService.getOtherPositions(employee.getPosition().getId());
		model.addAttribute("departments", departments);
		model.addAttribute("positions", positions);
		model.addAttribute("employee", employee);
		return "admin/update_employ";
	}
	@PostMapping("/updated_emp/{id}")
	public String saveUpdateEmployees(Model model,
			@PathVariable("id") Integer id,
			@Valid @ModelAttribute("employee") Employee updateEmployee,
			BindingResult result,
			@RequestParam("idPosition") Integer idPos,
			@RequestParam("idDepartment") Integer idDep,
			@RequestParam("image") MultipartFile multipartFile) throws Exception {
		log.info("id pos: "+idPos+" id department: "+idDep);
		Employee employee = employeeService.getEmployeeById(id);
		List<Department> departments = departmentService.getOtherDepartments(employee.getDepartment().getId());
		List<Position> positions = positionService.getOtherPositions(employee.getPosition().getId());
		
		if (result.hasErrors()){
			log.info("Error data");
			model.addAttribute("departments", departments);
			model.addAttribute("employee",employee);
            model.addAttribute("positions",positions);
            model.addAttribute("errorData", "Dữ liệu nhập vào không hợp lệ");
            return "admin/update_employ";
        }
		Department updatedDepartment = departmentService.getDepartmentById(idDep);
		Position updatedPosition = positionService.getPositionById(idPos);
		updateEmployee.setId(employee.getId());
		updateEmployee.setDepartment(updatedDepartment);
		updateEmployee.setPosition(updatedPosition);
		
		if(employeeService.checkUpdatedPositionIsOccupied(updateEmployee)) {
			model.addAttribute("employee",employee);
			model.addAttribute("departments", departments);
			model.addAttribute("positions",positions);
            model.addAttribute("errorPosition", "Vị trí này hiện tại không còn trống");
            return "admin/update_employ";
		}
		else {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			if(fileName.equals("")) updateEmployee.setPhoto(employee.getPhoto());
			else updateEmployee.setPhoto(fileName);
			employeeService.saveUpdateEmployee(employee,updateEmployee);
			if(!fileName.equals("")) {
				String uploadDir = "./src/main/resources/static/img/user/" + employee.getCode();
				Path uploadPath = Paths.get(uploadDir);
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				else {
					File file = new File(uploadDir);
					deleteFiles(file);
				}
				InputStream inputStream = multipartFile.getInputStream();
				Path filePath = uploadPath.resolve(fileName);
				System.out.println(filePath.toFile().getAbsolutePath());
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}
			id = employee.getDepartment().getId();
			log.info("Url: "+"redirect:/depart/employees/{"+id+"}");
			String url = "redirect:/depart/employees/"+id+"";
			return url;
		}
		
	}
	@GetMapping("/delete/{id}")
	public String deleteEmployee(Model model,@PathVariable("id") Integer id) {
		Employee employee = employeeService.getEmployeeById(id);
		id = employee.getDepartment().getId();
		employeeService.inactiveEmployee(employee);
		return "redirect:/depart/employees/{id}";
	}
	//delete all file in a directory
	public static void deleteFiles(File dirPath) {
		File filesList[] = dirPath.listFiles();
		for(File file:filesList) {
			if(file.isFile()) file.delete();
			else deleteFiles(file);
		}
	}
	//delete an entire directory
	public static void deleteDirectory(File dirPath) {
		for(File subfile : dirPath.listFiles()) {
			if(subfile.isDirectory()) {
				deleteDirectory(subfile);
			}
			subfile.delete();
		}
	}
}
