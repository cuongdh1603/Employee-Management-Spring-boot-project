package fpt.com.vn.demo.repositories;

import fpt.com.vn.demo.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    Department findDepartmentByName(@Param("name") String name);
}
