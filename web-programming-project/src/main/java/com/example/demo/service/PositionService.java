package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Position;
import com.example.demo.repository.PositionRepository;

@Service
public class PositionService {
	@Autowired
	private PositionRepository positionRepository;
	public List<Position> getAllPositions(){
		return positionRepository.findAll();
	}
	public Position getPositionById(Integer id) {
		Optional<Position> optional = positionRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else return null;
	}
	public List<Position> getOtherPositions(Integer id){
		return positionRepository.findOtherPositions(id);
	}
}
