package th.mfu.service;

import th.mfu.domain.Course;
import th.mfu.repository.CourseRepository;
import th.mfu.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO create(CourseDTO dto) {
        Course course = new Course(dto.getName(), dto.getDescription(), dto.getCredits());
        Course saved = courseRepository.save(course);
        return toDTO(saved);
    }

    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public Optional<CourseDTO> findById(Long id) {
        return courseRepository.findById(id).map(this::toDTO);
    }

    public CourseDTO update(Long id, CourseDTO dto) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setCredits(dto.getCredits());
        
        Course updated = courseRepository.save(course);
        return toDTO(updated);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    private CourseDTO toDTO(Course course) {
        return new CourseDTO(course.getId(), course.getName(), course.getDescription(), course.getCredits());
    }
}