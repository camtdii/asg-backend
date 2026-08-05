package th.mfu.service;

import th.mfu.domain.Enrollment;
import th.mfu.domain.Student;
import th.mfu.domain.Course;
import th.mfu.repository.EnrollmentRepository;
import th.mfu.repository.StudentRepository;
import th.mfu.repository.CourseRepository;
import th.mfu.dto.EnrollmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public EnrollmentDTO create(EnrollmentDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        
        Course course = courseRepository.findById(dto.getCourseId())
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + dto.getCourseId()));
        
        Enrollment enrollment = new Enrollment(student, course);
        enrollment.setStatus(dto.getStatus());
        
        Enrollment saved = enrollmentRepository.save(enrollment);
        return toDTO(saved);
    }

    public List<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public Optional<EnrollmentDTO> findById(Long id) {
        return enrollmentRepository.findById(id).map(this::toDTO);
    }

    public List<EnrollmentDTO> findByStudentId(Long studentId) {
        return enrollmentRepository.findAll().stream()
            .filter(e -> e.getStudent().getId().equals(studentId))
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public List<EnrollmentDTO> findByCourseId(Long courseId) {
        return enrollmentRepository.findAll().stream()
            .filter(e -> e.getCourse().getId().equals(courseId))
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public EnrollmentDTO update(Long id, EnrollmentDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
        
        enrollment.setStatus(dto.getStatus());
        
        Enrollment updated = enrollmentRepository.save(enrollment);
        return toDTO(updated);
    }

    public void delete(Long id) {
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentDTO toDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
            enrollment.getId(),
            enrollment.getStudent().getId(),
            enrollment.getCourse().getId(),
            enrollment.getEnrolledAt(),
            enrollment.getStatus()
        );
    }
}