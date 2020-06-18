package com.example.mongo.controller;

import com.example.mongo.entity.Student;
import com.example.mongo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/5 0:30
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @GetMapping("/{id}")
    public Student findStudentById(@PathVariable("id") String id){
        return studentService.findStudentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable("id") String id){
        studentService.deleteStudent(id);
    }

    @GetMapping("/list")
    public List<Student> findAllStudent(){
        return studentService.findAllStudent();
    }

}
