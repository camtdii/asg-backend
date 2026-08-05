package th.mfu.service;

import th.mfu.domain.Student;
import th.mfu.domain.Profile;
import th.mfu.repository.StudentRepository;
import th.mfu.repository.ProfileRepository;
import th.mfu.dto.StudentDTO;
import th.mfu.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public StudentDTO create(StudentDTO dto) {
        Student student = new Student(dto.getName(), dto.getEmail());
        
        if (dto.getProfile() != null) {
            Profile profile = new Profile(dto.getProfile().getBio(), dto.getProfile().getPhone(), dto.getProfile().getAddress());
            student.setProfile(profile);
        }
        
        Student saved = studentRepository.save(student);
        return toDTO(saved);
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public Optional<StudentDTO> findById(Long id) {
        return studentRepository.findById(id).map(this::toDTO);
    }

    public Optional<StudentDTO> findByEmail(String email) {
        return Optional.ofNullable(studentRepository.findByEmail(email)).map(this::toDTO);
    }

    public StudentDTO update(Long id, StudentDTO dto) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        
        if (dto.getProfile() != null) {
            Profile profile = student.getProfile();
            if (profile == null) {
                profile = new Profile();
                student.setProfile(profile);
            }
            profile.setBio(dto.getProfile().getBio());
            profile.setPhone(dto.getProfile().getPhone());
            profile.setAddress(dto.getProfile().getAddress());
        }
        
        Student updated = studentRepository.save(student);
        return toDTO(updated);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDTO toDTO(Student student) {
        StudentDTO dto = new StudentDTO(student.getId(), student.getName(), student.getEmail());
        if (student.getProfile() != null) {
            Profile p = student.getProfile();
            dto.setProfile(new ProfileDTO(p.getId(), p.getBio(), p.getPhone(), p.getAddress()));
        }
        return dto;
    }
}