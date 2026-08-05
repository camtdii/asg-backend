package th.camt.controller;

import th.camt.dto.ProfileDTO;
import th.camt.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/student/{studentId}")
    public ResponseEntity<ProfileDTO> create(@PathVariable Long studentId, @RequestBody ProfileDTO dto) {
        ProfileDTO created = profileService.create(studentId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> findById(@PathVariable Long id) {
        Optional<ProfileDTO> profile = profileService.findById(id);
        return profile.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ProfileDTO> findByStudentId(@PathVariable Long studentId) {
        Optional<ProfileDTO> profile = profileService.findByStudentId(studentId);
        return profile.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable Long id, @RequestBody ProfileDTO dto) {
        try {
            ProfileDTO updated = profileService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            profileService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}