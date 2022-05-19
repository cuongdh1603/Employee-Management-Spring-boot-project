package com.example.demo.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
/*
Table: detail_timekeeping
Columns:
detail_timekeeping_id int AI PK 
detail_timekeeping_date date 
detail_timekeeping_work tinyint(1) 
detail_timekeeping_leave tinyint(1) 
detail_timekeeping_rest tinyint(1) 
employee_id int 
salary_id int 
 **/
@Entity
@Table(name = "detail_timekeeping")
public class TimeKeeping {
	@Id
	@Column(name = "detail_timekeeping_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "detail_timekeeping_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	@Column(name = "detail_timekeeping_work")
	private Integer work;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getWork() {
		return work;
	}
	public void setWork(Integer work) {
		this.work = work;
	}
	public TimeKeeping(Integer id, Date date, Integer work) {
		super();
		this.id = id;
		this.date = date;
		this.work = work;
	}
	public TimeKeeping() {
		super();
		LocalDate currentDate = LocalDate.now();
		Date date = Date.from(currentDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		this.setDate(date);
		this.setWork(0);
	}
	/*-------------------------------------------RELATIONSHIP----------------------------------------------------*/
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@ManyToOne
	@JoinColumn(name = "salary_id")
	private Wage wage;
	public Wage getWage() {
		return wage;
	}
	public void setWage(Wage wage) {
		this.wage = wage;
	}
	
}
