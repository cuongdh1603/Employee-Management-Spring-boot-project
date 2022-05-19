package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/*
department_id
department_code
department_name
department_description
 */
@Entity
@Table(name = "department")
public class Department {
	@Id
	@Column(name = "department_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "department_code")
	private String code;
	@Column(name = "department_name")
	private String name;
	@Column(name = "department_description")
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Department(Integer id, String code, String name, String description) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}
	public Department() {
		super();
	}
	/*----------------------------------------RELATIONSHIP----------------------------------------*/
	@OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
	private List<Employee> employeeList;
}
