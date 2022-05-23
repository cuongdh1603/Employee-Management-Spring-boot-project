package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/*
account_id
account_username
account_password
account_active
role_id
 */
@Entity
@Table(name = "account")
public class Account {
	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "account_username")
	@NotBlank(message = "Không được để trống")
	@Size(min=5,max=15,message = "Độ dài tên đăng nhập tối thiếu từ 5 kí tự trở lên")
	private String username;
	@Column(name = "account_password")
	@NotBlank(message = "Không được để trống")
	@Size(min=6,message = "Yêu cầu mật khẩu từ 6 kí tự trở lên")
	private String password;
	@Column(name = "account_active")
	private Integer isActive;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public Account(Integer id, String username, String password, Integer isActive) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
	}
	public Account() {
		super();
		this.isActive = 1;
	}
	/*----------------------------------------RELATIONSHIP----------------------------------------*/
	@OneToOne(mappedBy = "account")
    private Employee employee;
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@ManyToOne()
	@JoinColumn(name = "role_id")
	private Role role;
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
