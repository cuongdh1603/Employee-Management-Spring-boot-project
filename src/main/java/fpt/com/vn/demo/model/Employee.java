package fpt.com.vn.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "id_employee")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "em_name")
    private String name;
    @Column(name = "em_year")
    private int birthYear;
    @Column(name = "em_code")
    private String code;
    @Column(name = "em_address")
    private String address;
    @Column(name = "em_start")
    private Date startDate;
    @Column(name = "em_retire")
    private Date retireDate;
    @Column(name = "em_level")
    private String level;
    @Column(name = "em_languages")
    private String foreignLanguage;
    @Column(name = "em_computer")
    private String computer;
    @Column(name = "em_award")
    private String award;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(String foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public Employee(int id, String name, int birthYear, String code, String address, Date startDate, Date retireDate, String level, String foreignLanguage, String computer, String award) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.code = code;
        this.address = address;
        this.startDate = startDate;
        this.retireDate = retireDate;
        this.level = level;
        this.foreignLanguage = foreignLanguage;
        this.computer = computer;
        this.award = award;
    }

    public Employee() {
    }
    /*---------------------------------------RELATIONSHIP----------------------------------------*/
    @ManyToOne
    @JoinColumn(name = "id_department")
    public Department department;
    @ManyToOne
    @JoinColumn(name = "id_position")
    public Position position;
    @OneToOne
    @JoinColumn(name = "id_account")
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
}
