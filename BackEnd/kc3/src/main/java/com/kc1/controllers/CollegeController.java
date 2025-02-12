package com.kc1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kc1.dto.CollegeDto;
import com.kc1.entities.College;
import com.kc1.exceptions.ResourceNotFoundException;
import com.kc1.services.CollegeService;

@RestController
@RequestMapping("/colleges")
@CrossOrigin(origins = "http://localhost:3000")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;
    
    @PostMapping("/add")
    public ResponseEntity<?> addCollege(@RequestBody CollegeDto collegeDto) {
        return ResponseEntity.ok(collegeService.addCollege(collegeDto));
    }

    @PutMapping("/update/{collegeId}")
    public ResponseEntity<?> updateCollege(@PathVariable Integer collegeId, @RequestBody CollegeDto college) {
    	 try {
             College updatedCollege = collegeService.updateCollege(collegeId, college);
             return ResponseEntity.ok(updatedCollege);
         } catch (ResourceNotFoundException e) {
             return ResponseEntity.status(404).build(); 
         }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCollege(@PathVariable int id) {
        try {
            collegeService.deleteCollege(id);
            return ResponseEntity.noContent().build(); 
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).build(); 
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllColleges() {
        return ResponseEntity.ok(collegeService.getAllColleges());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<College>> searchColleges(@RequestParam("name") String name) {
        try {
            List<College> colleges = collegeService.searchCollegesByName(name);
            return ResponseEntity.ok(colleges);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollegeById(@PathVariable int id) {
        try {
            College college = collegeService.getCollegeById(id);
            return ResponseEntity.ok(college);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<List<College>> getCollegesByType(@PathVariable String type) {
        try {
            College.Type collegeType = College.Type.valueOf(type.toUpperCase());
            List<College> colleges = collegeService.getCollegesByType(collegeType);
            return ResponseEntity.ok(colleges);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();  
        }
    }
    
}
