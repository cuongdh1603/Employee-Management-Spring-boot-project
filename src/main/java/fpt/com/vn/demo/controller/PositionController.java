package fpt.com.vn.demo.controller;

import fpt.com.vn.demo.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PositionController {
    @Autowired
    private PositionService positionService;
    @GetMapping("/position/insert")
    public ResponseEntity<Object> insertPosition(){
        positionService.insertPosition();
        return new ResponseEntity<Object>("Insert successfully", HttpStatus.OK);
    }
}
