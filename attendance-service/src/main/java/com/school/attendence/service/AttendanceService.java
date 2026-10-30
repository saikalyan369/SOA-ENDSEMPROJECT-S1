package com.school.attendence.service;

import com.school.attendence.dto.AttendanceStudentResultResponse;
import com.school.attendence.entity.Attendance;
import com.school.attendence.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final RestClient.Builder restClientBuilder;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            @Qualifier("loadBalancedRestClientBuilder")
            RestClient.Builder restClientBuilder) {

        this.attendanceRepository = attendanceRepository;
        this.restClientBuilder = restClientBuilder;
    }

    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Attendance not found with ID: " + id
                        ));
    }

    public List<Attendance> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public Attendance updateAttendance(
            Long id,
            Attendance attendance) {

        Attendance existingAttendance =
                getAttendanceById(id);

        existingAttendance.setStudentId(
                attendance.getStudentId()
        );

        existingAttendance.setDate(
                attendance.getDate()
        );

        existingAttendance.setStatus(
                attendance.getStatus()
        );

        return attendanceRepository.save(existingAttendance);
    }

    public void deleteAttendance(Long id) {
        Attendance existingAttendance =
                getAttendanceById(id);

        attendanceRepository.delete(existingAttendance);
    }

    // Attendance → Student → Result
    public AttendanceStudentResultResponse
    getAttendanceWithStudentAndResults(Long studentId) {

        // 1. Get attendance from local database
        List<Attendance> attendance =
                getAttendanceByStudentId(studentId);

        // 2. Call Student Service
        Object studentDetails =
                restClientBuilder
                        .build()
                        .get()
                        .uri(
                                "http://student-service/students/{studentId}/results",
                                studentId
                        )
                        .retrieve()
                        .body(Object.class);

        // 3. Return combined response
        return new AttendanceStudentResultResponse(
                attendance,
                studentDetails
        );
    }
}