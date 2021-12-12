package fpt.com.vn.demo.service;

import fpt.com.vn.demo.model.Department;
import fpt.com.vn.demo.model.Employee;
import fpt.com.vn.demo.model.Position;
import fpt.com.vn.demo.repositories.DepartmentRepository;
import fpt.com.vn.demo.repositories.EmployeeRepository;
import fpt.com.vn.demo.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;
    public void insertEmployee(String dp_name,String ps_name){
        Department de = departmentRepository.findDepartmentByName(dp_name);
        Position po = positionRepository.findPositionByName(ps_name);
        if(de != null && po != null){
            Employee em = new Employee();
            em.setName("Steven");
            em.setBirthYear(2001);
            em.setAddress("Ha Noi");
            em.setCode("AD_VP");
            em.setStartDate(getDate("2010-12-03"));
            em.setDepartment(de);
            em.setPosition(po);
            employeeRepository.save(em);
        }

    }
    public static java.util.Date getDate(String date){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date d = format.parse(date);
            java.sql.Date d1 = new java.sql.Date(d.getTime());
            return d1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
