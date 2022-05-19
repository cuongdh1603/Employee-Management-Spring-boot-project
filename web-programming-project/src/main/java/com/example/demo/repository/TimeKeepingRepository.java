package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TimeKeeping;
@Repository
public interface TimeKeepingRepository extends JpaRepository<TimeKeeping, Integer>{
	@Query(value = "select * from detail_timekeeping where detail_timekeeping_date = :date and employee_id = :id",nativeQuery = true)
	Optional<TimeKeeping> findByDateAndEmployeeId(@Param("date") String date,@Param("id") Integer id);
	Optional<TimeKeeping> findById(Integer id);
}
