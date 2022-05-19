package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	Optional<Department> findById(Integer id);
	@Query(value="select * from department where department_id != :de",nativeQuery=true)
	List<Department> findOtherDepartments(@Param("de") Integer id);
}
