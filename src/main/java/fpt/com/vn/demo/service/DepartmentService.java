package fpt.com.vn.demo.service;

import fpt.com.vn.demo.model.Department;
import fpt.com.vn.demo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    public void insertDepartment(){
        Department de = new Department();
        de.setName("Advertising");
        de.setCode("ADV");
        //de.setDescription("");
        departmentRepository.save(de);
    }
}
