package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Wage;
@Repository
public interface WageRepository extends JpaRepository<Wage, Integer>{
	@Query(value = "select * from salary where salary_month = :month and employee_id = :id",nativeQuery = true)
	Optional<Wage> findWageByMonthAndEmployeeId(@Param("month") Integer month,@Param("id") Integer id);
	@Query(value = "select * from salary where salary_month = :month and salary_year = :year and employee_id = :id",nativeQuery = true)
	Optional<Wage> findWageByTimeAndEmployeeId(@Param("month") Integer month,@Param("year") Integer year,@Param("id") Integer id);
}
