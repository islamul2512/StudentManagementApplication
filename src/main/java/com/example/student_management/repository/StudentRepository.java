package com.example.student_management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management.model.Student;
@Repository
public interface StudentRepository extends MongoRepository<Student, String>{

}
