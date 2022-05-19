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
@Entity
@Table(name = "position")
public class Position {
	@Id
	@Column(name = "position_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "position_code")
	private String code;
	@Column(name = "position_name")
	private String name;
	@Column(name = "position_description")
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
	public Position(Integer id, String code, String name, String description) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}
	public Position() {
		super();
	}
	/*----------------------------------------RELATIONSHIP----------------------------------------*/
	@OneToMany(mappedBy = "position",fetch = FetchType.LAZY)
	private List<Employee> employeeList;
}
/*

position_id
position_code
position_name
position_description
*/
