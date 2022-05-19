package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	@Query(value="select * from employee where employee_active = 1",nativeQuery=true)
	List<Employee> findActiveEmployee();
	@Query(value="select * from employee where employee_active = 1 and department_id = :id",nativeQuery=true)
	List<Employee> findByDepartmentId(@Param("id") Integer id);
	@Query(value="select max(employee_id) from employee where department_id = :de",nativeQuery=true)
	Integer findMaxId(@Param("de") Integer idDepartment);
	@Query(value="select * from employee where employee_active = 1 and department_id = :de and position_id = :po",nativeQuery=true)
	Employee findByDepartmentAndPosition(@Param("de") Integer idDepartment,@Param("po") Integer idPosition);
	@Query(value="select * from employee where employee_active = 1 and employee_id = :id",nativeQuery=true)
	Optional<Employee> findById(@Param("id") Integer id);
	@Query(value="select * from employee where employee_active = 1 and department_id = :id and position_id != 1",nativeQuery=true)
	List<Employee> findEmployeesToManagerByDepartmentId(@Param("id") Integer id);
	@Query(value="select * from employee where employee_active = 1 and department_id != 6 and position_id = 1",nativeQuery=true)
	List<Employee> findManagers();
//	@Transactional
//	@Modifying
//	@Query(value="update employee set employee_starting_wage = :wg where employee_id = :id",nativeQuery=true)
//	void updateWage(@Param("id") Integer id,@Param("wg") long wage);
}
