package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/*
salary_id int AI PK 
salary_month int 
salary_year int 
salary_work int 
salary_leave int 
salary_rest int 
salary_bonus bigint 
salary_basic bigint 
salary_total bigint 
employee_id int
 */

@Entity
@Table(name = "salary")
public class Wage {
	@Id
	@Column(name = "salary_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "salary_month")
	private Integer month;
	@Column(name = "salary_year")
	private Integer year;
	@Column(name = "salary_work")
	private Integer work;
	@Column(name = "salary_leave")
	private Integer leave;
	@Column(name = "salary_rest")
	private Integer rest;
	@Column(name = "salary_bonus")
	private Long bonus;
	@Column(name = "salary_basic")
	private Long basic;
	@Column(name = "salary_total")
	private Long total;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getWork() {
		return work;
	}
	public void setWork(Integer work) {
		this.work = work;
	}
	public Integer getLeave() {
		return leave;
	}
	public void setLeave(Integer leave) {
		this.leave = leave;
	}
	public Integer getRest() {
		return rest;
	}
	public void setRest(Integer rest) {
		this.rest = rest;
	}
	public Long getBonus() {
		return bonus;
	}
	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}
	public Long getBasic() {
		return basic;
	}
	public void setBasic(Long basic) {
		this.basic = basic;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Wage(Integer id, Integer month, Integer year, Integer work, Integer leave, Integer rest, Long bonus, Long basic,
			Long total) {
		super();
		this.id = id;
		this.month = month;
		this.year = year;
		this.work = work;
		this.leave = leave;
		this.rest = rest;
		this.bonus = bonus;
		this.basic = basic;
		this.total = total;
	}
	public Wage() {
		super();
		LocalDate currentDate = LocalDate.now();
		this.setMonth(currentDate.getMonthValue());
		this.setYear(currentDate.getYear());
		this.setLeave(0);
		this.setRest(0);
		this.setWork(0);
		this.setTotal(0L);
	}
	/*-------------------------------------------RELATIONSHIP----------------------------------------------------*/
	@ManyToOne
	@JoinColumn(name = "employee_id")
	public Employee employee;
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
}
