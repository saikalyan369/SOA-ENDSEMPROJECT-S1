package com.school.attendence.dto;

import com.school.attendence.entity.Attendance;

import java.util.List;

public class AttendanceStudentResultResponse {

    private List<Attendance> attendance;
    private Object studentDetails;

    public AttendanceStudentResultResponse() {
    }

    public AttendanceStudentResultResponse(
            List<Attendance> attendance,
            Object studentDetails) {

        this.attendance = attendance;
        this.studentDetails = studentDetails;
    }

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public Object getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(Object studentDetails) {
        this.studentDetails = studentDetails;
    }
}