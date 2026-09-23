package com.school.student.service;

import com.school.student.dto.StudentResultResponse;
import com.school.student.entity.Student;
import com.school.student.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RestClient.Builder restClientBuilder;

    public StudentService(
            StudentRepository studentRepository,
            @Qualifier("loadBalancedRestClientBuilder")
            RestClient.Builder restClientBuilder) {

        this.studentRepository = studentRepository;
        this.restClientBuilder = restClientBuilder;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with ID: " + id));
    }

    public Student updateStudent(Long id, Student student) {
        Student existingStudent = getStudentById(id);

        existingStudent.setName(student.getName());
        existingStudent.setStudentClass(student.getStudentClass());
        existingStudent.setSection(student.getSection());

        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        Student existingStudent = getStudentById(id);
        studentRepository.delete(existingStudent);
    }

    // Student Service -> Result Service
    public StudentResultResponse getStudentWithResults(Long studentId) {

        Student student = getStudentById(studentId);

        List<Object> results = restClientBuilder
                .build()
                .get()
                .uri(
                        "http://result-service/results/student/{studentId}",
                        studentId
                )
                .retrieve()
                .body(List.class);

        return new StudentResultResponse(student, results);
    }
}