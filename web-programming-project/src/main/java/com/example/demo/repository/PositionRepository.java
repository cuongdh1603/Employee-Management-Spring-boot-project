package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer>{
	Optional<Position> findById(Integer id);
	Position findByCode(String code);
	@Query(value="select * from position where position_id != :po",nativeQuery=true)
	List<Position> findOtherPositions(@Param("po") Integer id);
}
