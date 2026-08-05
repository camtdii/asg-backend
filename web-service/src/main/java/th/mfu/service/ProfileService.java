package th.mfu.service;

import th.mfu.domain.Profile;
import th.mfu.domain.Student;
import th.mfu.repository.ProfileRepository;
import th.mfu.repository.StudentRepository;
import th.mfu.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private StudentRepository studentRepository;

    public ProfileDTO create(Long studentId, ProfileDTO dto) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        Profile profile = new Profile(dto.getBio(), dto.getPhone(), dto.getAddress());
        profile.setStudent(student);
        
        Profile saved = profileRepository.save(profile);
        return toDTO(saved);
    }

    public Optional<ProfileDTO> findById(Long id) {
        return profileRepository.findById(id).map(this::toDTO);
    }

    public Optional<ProfileDTO> findByStudentId(Long studentId) {
        return profileRepository.findAll().stream()
            .filter(p -> p.getStudent().getId().equals(studentId))
            .findFirst()
            .map(this::toDTO);
    }

    public ProfileDTO update(Long id, ProfileDTO dto) {
        Profile profile = profileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
        
        profile.setBio(dto.getBio());
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        
        Profile updated = profileRepository.save(profile);
        return toDTO(updated);
    }

    public void delete(Long id) {
        profileRepository.deleteById(id);
    }

    private ProfileDTO toDTO(Profile profile) {
        return new ProfileDTO(profile.getId(), profile.getBio(), profile.getPhone(), profile.getAddress());
    }
}