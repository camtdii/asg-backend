package th.camt.dto;

import javax.validation.constraints.NotNull;

public class EnrollmentDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private java.time.LocalDateTime enrolledAt;

    private String status = "ACTIVE";

    // Constructors
    public EnrollmentDTO() {}

    public EnrollmentDTO(Long id, Long studentId, Long courseId, java.time.LocalDateTime enrolledAt, String status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
        this.status = status != null ? status : "ACTIVE";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public java.time.LocalDateTime getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(java.time.LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}