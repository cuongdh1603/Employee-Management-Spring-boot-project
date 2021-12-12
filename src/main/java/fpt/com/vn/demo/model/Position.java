package fpt.com.vn.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @Column(name = "id_position")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "position_name")
    private String name;
    @Column(name = "position_code")
    private String code;
    @Column(name = "position_descript")
    private String description;
    @OneToMany(mappedBy = "position")
    private List<Employee> employeeList;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Position(int id, String name, String code, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public Position() {
    }
}
