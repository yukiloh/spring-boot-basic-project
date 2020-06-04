package com.example.mongo.service;

import com.example.mongo.entity.Student;
import com.example.mongo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/5 0:24
 * 使用spring提供的方法来进行crud. 也可以使用命名查询(在StudentRepository下可以看到),或者源生查询
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * 添加学生信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Student addStudent(Student student) {
        return studentRepository.save(student);
        //save还可以进行更新操作
        //insert也可以插入数据,如果主键id已存在会报错
    }

    /**
     * 根据id删除学生
     */
    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    /**
     * 更新学生信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Student updateStudent(Student student) {
        Student oldStudent = findStudentById(student.getId());
        if (oldStudent != null){
            oldStudent.setStudentId(student.getStudentId());
            oldStudent.setAge(student.getAge());
            oldStudent.setName(student.getName());
            oldStudent.setGender(student.getGender());
            return studentRepository.save(oldStudent);
        } else {
            return null;
        }
    }

    /**
     * 根据 id 查询学生信息
     */
    public Student findStudentById(String id) {
        Optional<Student> one = studentRepository.findById(id);
        return one.orElse(null);
    }

    /**
     * 查询学生信息列表
     */
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

}
