package com.school.student.dto;

import com.school.student.entity.Student;

import java.util.List;

public class StudentResultResponse {

    private Student student;
    private List<Object> results;

    public StudentResultResponse() {
    }

    public StudentResultResponse(Student student, List<Object> results) {
        this.student = student;
        this.results = results;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Object> getResults() {
        return results;
    }

    public void setResults(List<Object> results) {
        this.results = results;
    }
}