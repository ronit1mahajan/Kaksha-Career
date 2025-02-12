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

import com.kc1.dto.AddCourseDto;
import com.kc1.dto.CourseDto;
import com.kc1.entities.Course;
import com.kc1.exceptions.ResourceNotFoundException;
import com.kc1.services.CourseService;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    @Autowired
    private CourseService courseService;
    
    
    @PostMapping("/add")
    public ResponseEntity<CourseDto> addCourse(@RequestBody AddCourseDto courseDto) {
        CourseDto createdCourse = courseService.addCourse(courseDto);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable int courseId, @RequestBody CourseDto courseDto) {
        try {
            CourseDto updatedCourse = courseService.updateCourse(courseId, courseDto);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.noContent().build(); 
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/get/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable int courseId) {
        try {
            CourseDto courseDto = courseService.getCourseById(courseId);
            return new ResponseEntity<>(courseDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCoursesWithCollege();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<List<Course>> searchCoursesByName(@RequestParam String courseName) {
        List<Course> courses = courseService.searchCoursesByName(courseName);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }


    @PostMapping("/aCintoC")
    public ResponseEntity<?> addCourseToCollege(@RequestParam Integer collegeId, @RequestParam Integer courseId) {
        try {
            CourseDto course = courseService.addCourseToCollege(collegeId, courseId);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error adding course: " + e.getMessage());
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam("name") String name) {
        try {
            List<Course> courses = courseService.searchCoursesByName(name);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
