package fpt.com.vn.demo.service;

import fpt.com.vn.demo.model.Position;
import fpt.com.vn.demo.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;
    public void insertPosition(){
        Position po = new Position();
        po.setName("Manager");
        po.setCode("MN");
        po.setDescription("Make decisions");
        positionRepository.save(po);
    }
}
