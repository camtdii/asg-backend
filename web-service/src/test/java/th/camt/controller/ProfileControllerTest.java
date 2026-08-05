package th.camt.controller;

import th.camt.dto.ProfileDTO;
import th.camt.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    @Test
    void create_ShouldReturnCreatedProfile() {
        // Given
        ProfileDTO inputDTO = new ProfileDTO();
        inputDTO.setBio("Software Engineer");
        inputDTO.setPhone("081-234-5678");
        inputDTO.setAddress("123 Main St, Bangkok");

        ProfileDTO expectedDTO = new ProfileDTO(1L, "Software Engineer", "081-234-5678", "123 Main St, Bangkok");
        when(profileService.create(1L, inputDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<ProfileDTO> response = profileController.create(1L, inputDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Software Engineer", response.getBody().getBio());
        verify(profileService).create(1L, inputDTO);
    }

    @Test
    void findById_WhenProfileExists_ShouldReturnProfile() {
        // Given
        ProfileDTO expectedDTO = new ProfileDTO(1L, "Software Engineer", "081-234-5678", "123 Main St, Bangkok");
        when(profileService.findById(1L)).thenReturn(Optional.of(expectedDTO));

        // When
        ResponseEntity<ProfileDTO> response = profileController.findById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Software Engineer", response.getBody().getBio());
        verify(profileService).findById(1L);
    }

    @Test
    void findById_WhenProfileNotExists_ShouldReturnNotFound() {
        // Given
        when(profileService.findById(999L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<ProfileDTO> response = profileController.findById(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(profileService).findById(999L);
    }

    @Test
    void findByStudentId_WhenProfileExists_ShouldReturnProfile() {
        // Given
        ProfileDTO expectedDTO = new ProfileDTO(1L, "Software Engineer", "081-234-5678", "123 Main St, Bangkok");
        when(profileService.findByStudentId(1L)).thenReturn(Optional.of(expectedDTO));

        // When
        ResponseEntity<ProfileDTO> response = profileController.findByStudentId(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Software Engineer", response.getBody().getBio());
        verify(profileService).findByStudentId(1L);
    }

    @Test
    void findByStudentId_WhenProfileNotExists_ShouldReturnNotFound() {
        // Given
        when(profileService.findByStudentId(999L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<ProfileDTO> response = profileController.findByStudentId(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(profileService).findByStudentId(999L);
    }

    @Test
    void update_WhenProfileExists_ShouldReturnUpdatedProfile() {
        // Given
        ProfileDTO inputDTO = new ProfileDTO();
        inputDTO.setBio("Senior Software Engineer");
        inputDTO.setPhone("081-234-5678");
        inputDTO.setAddress("456 New St, Bangkok");

        ProfileDTO expectedDTO = new ProfileDTO(1L, "Senior Software Engineer", "081-234-5678", "456 New St, Bangkok");
        when(profileService.update(1L, inputDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<ProfileDTO> response = profileController.update(1L, inputDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Senior Software Engineer", response.getBody().getBio());
        verify(profileService).update(1L, inputDTO);
    }

    @Test
    void update_WhenProfileNotExists_ShouldReturnNotFound() {
        // Given
        ProfileDTO inputDTO = new ProfileDTO();
        inputDTO.setBio("Senior Software Engineer");
        when(profileService.update(999L, inputDTO)).thenThrow(new RuntimeException("Profile not found"));

        // When
        ResponseEntity<ProfileDTO> response = profileController.update(999L, inputDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(profileService).update(999L, inputDTO);
    }

    @Test
    void delete_WhenProfileExists_ShouldReturnNoContent() {
        // Given
        doNothing().when(profileService).delete(1L);

        // When
        ResponseEntity<Void> response = profileController.delete(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(profileService).delete(1L);
    }

    @Test
    void delete_WhenProfileNotExists_ShouldReturnNotFound() {
        // Given
        doThrow(new RuntimeException("Profile not found")).when(profileService).delete(999L);

        // When
        ResponseEntity<Void> response = profileController.delete(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(profileService).delete(999L);
    }
}