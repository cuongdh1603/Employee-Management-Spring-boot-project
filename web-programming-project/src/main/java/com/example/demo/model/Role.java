package com.example.demo.model;

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
Table: role
Columns:
role_id int AI PK 
role_code varchar(10) 
role_name varchar(45)
 */
@Entity
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "role_code")
	private String code;
	@Column(name = "role_name")
	private String name;

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
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public Role(Integer id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}
	public Role() {
		super();
	}
	/*----------------------------------------RELATIONSHIP----------------------------------------*/
	@OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
	private List<Account> accounts; 
	 
	
}
