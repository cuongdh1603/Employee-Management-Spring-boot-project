package com.example.demo.model;

import java.text.NumberFormat;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@Column(name = "employee_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "employee_code")
	private String code;
	
	@Column(name = "employee_photo")
	private String photo;
	
	@Column(name = "employee_firstname")
	@NotBlank(message = "Không được để trống")
	private String firstname;
	
	@Column(name = "employee_lastname")
	@NotBlank(message = "Không được để trống")
	private String lastname;
	
	@Column(name = "employee_gender")
	private Integer gender;
	
	@Column(name = "employee_email")
	@NotBlank(message = "Không được để trống")
	@Pattern(regexp ="^[A-Za-z0-9+_.-]+@(.+)$",message="Email không hợp lệ")
	private String email;
	
	@Column(name = "employee_phonenumber")
	@NotBlank(message = "Không được để trống")
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phoneNumber;
	
	@Column(name = "employee_dob")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Không được để trống")
	private Date dob;
	
	@Column(name = "employee_date_start")
	@Past(message = "Thời gian không hợp lệ")
    @NotNull(message = "Không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;
	
	@Column(name = "employee_address")
	@NotBlank(message = "Không được để trống")
	private String address;
	
	@Column(name = "employee_description")
	private String description;
	
	@Column(name = "employee_wage")
	@NotNull(message = "Không được để trống")
	private Long wage;
	
	@Column(name = "employee_starting_wage")
	private Long startingWage;
	
	@Column(name = "employee_recruit_time")
	private Float recruitTime;
	
	@Column(name = "employee_active")
	private Integer isActive;
	
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getDateStart() {
		return dateStart;
	}
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getWage() {
		return wage;
	}
	public void setWage(Long wage) {
		this.wage = wage;
	}
	public Long getStartingWage() {
		return startingWage;
	}
	public void setStartingWage(Long startingWage) {
		this.startingWage = startingWage;
	}
	public Float getRecruitTime() {
		return recruitTime;
	}
	public void setRecruitTime(Float recruitTime) {
		this.recruitTime = recruitTime;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}	
	
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhotoPath() {
		if(photo==null) return null;
		return "/img/user/"+code+"/"+photo;
	}
	public String getFormatStartingWage() {
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		return myFormat.format(getStartingWage());
	}
	public String getFormatWage() {
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		return myFormat.format(getWage());
	}
	public Employee(Integer id, String code, String photo, String firstname, String lastname, Integer gender,
			String email, String phoneNumber, Date dob, Date dateStart, String address, String description, Long wage,
			Long startingWage, Float recruitTime, Integer isActive) {
		super();
		this.id = id;
		this.code = code;
		this.photo = photo;
		this.firstname = firstname;
		this.lastname = lastname;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.dob = dob;
		this.dateStart = dateStart;
		this.address = address;
		this.description = description;
		this.wage = wage;
		this.startingWage = startingWage;
		this.recruitTime = recruitTime;
		this.isActive = isActive;
	}
	public Employee() {
		super();
	}
	/*----------------------------------------RELATIONSHIP----------------------------------------*/
	@ManyToOne
	@JoinColumn(name = "department_id")
	public Department department;
	
	@ManyToOne
	@JoinColumn(name = "position_id")
	public Position position;
	
	@OneToOne
	@JoinColumn(name = "account_id")
	public Account account;
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
	private List<TimeKeeping> timeKeepings;
	@OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
	private List<Wage> wages;

	public List<TimeKeeping> getTimeKeepings() {
		return timeKeepings;
	}
	public void setTimeKeepings(List<TimeKeeping> timeKeepings) {
		this.timeKeepings = timeKeepings;
	}
	public List<Wage> getWages() {
		return wages;
	}
	public void setWages(List<Wage> wages) {
		this.wages = wages;
	}
	
}
/*
employee_id int AI PK 
employee_code varchar(10) 
employee_photo varchar(250) 
employee_firstname varchar(45) 
employee_lastname varchar(45) 
employee_gender tinyint 
employee_dob date 
employee_email varchar(100) 
employee_phonenumber varchar(10) 
employee_date_start date 
employee_address varchar(250) 
employee_description varchar(250) 
employee_wage bigint 
employee_starting_wage bigint 
employee_recruit_time float 
employee_active tinyint(1) 
account_id int 
department_id int 
position_id int
 */