package th.mfu.controller;

import th.mfu.dto.EnrollmentDTO;
import th.mfu.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Test
    void create_ShouldReturnCreatedEnrollment() {
        // Given
        EnrollmentDTO inputDTO = new EnrollmentDTO();
        inputDTO.setStudentId(1L);
        inputDTO.setCourseId(1L);
        inputDTO.setStatus("ACTIVE");

        EnrollmentDTO expectedDTO = new EnrollmentDTO(1L, 1L, 1L, LocalDateTime.now(), "ACTIVE");
        when(enrollmentService.create(inputDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<EnrollmentDTO> response = enrollmentController.create(inputDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getStudentId());
        assertEquals(1L, response.getBody().getCourseId());
        verify(enrollmentService).create(inputDTO);
    }

    @Test
    void findAll_ShouldReturnListOfEnrollments() {
        // Given
        List<EnrollmentDTO> expectedEnrollments = Arrays.asList(
            new EnrollmentDTO(1L, 1L, 1L, LocalDateTime.now(), "ACTIVE"),
            new EnrollmentDTO(2L, 2L, 1L, LocalDateTime.now(), "ACTIVE")
        );
        when(enrollmentService.findAll()).thenReturn(expectedEnrollments);

        // When
        ResponseEntity<List<EnrollmentDTO>> response = enrollmentController.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(enrollmentService).findAll();
    }

    @Test
    void findById_WhenEnrollmentExists_ShouldReturnEnrollment() {
        // Given
        EnrollmentDTO expectedDTO = new EnrollmentDTO(1L, 1L, 1L, LocalDateTime.now(), "ACTIVE");
        when(enrollmentService.findById(1L)).thenReturn(Optional.of(expectedDTO));

        // When
        ResponseEntity<EnrollmentDTO> response = enrollmentController.findById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(enrollmentService).findById(1L);
    }

    @Test
    void findById_WhenEnrollmentNotExists_ShouldReturnNotFound() {
        // Given
        when(enrollmentService.findById(999L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<EnrollmentDTO> response = enrollmentController.findById(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(enrollmentService).findById(999L);
    }

    @Test
    void findByStudentId_ShouldReturnEnrollments() {
        // Given
        List<EnrollmentDTO> expectedEnrollments = Arrays.asList(
            new EnrollmentDTO(1L, 1L, 1L, LocalDateTime.now(), "ACTIVE")
        );
        when(enrollmentService.findByStudentId(1L)).thenReturn(expectedEnrollments);

        // When
        ResponseEntity<List<EnrollmentDTO>> response = enrollmentController.findByStudentId(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(enrollmentService).findByStudentId(1L);
    }

    @Test
    void findByCourseId_ShouldReturnEnrollments() {
        // Given
        List<EnrollmentDTO> expectedEnrollments = Arrays.asList(
            new EnrollmentDTO(1L, 1L, 1L, LocalDateTime.now(), "ACTIVE")
        );
        when(enrollmentService.findByCourseId(1L)).thenReturn(expectedEnrollments);

        // When
        ResponseEntity<List<EnrollmentDTO>> response = enrollmentController.findByCourseId(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(enrollmentService).findByCourseId(1L);
    }

    @Test
    void update_WhenEnrollmentExists_ShouldReturnUpdatedEnrollment() {
        // Given
        EnrollmentDTO inputDTO = new EnrollmentDTO();
        inputDTO.setStatus("COMPLETED");

        EnrollmentDTO expectedDTO = new EnrollmentDTO(1L, 1L, 1L, LocalDateTime.now(), "COMPLETED");
        when(enrollmentService.update(1L, inputDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<EnrollmentDTO> response = enrollmentController.update(1L, inputDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("COMPLETED", response.getBody().getStatus());
        verify(enrollmentService).update(1L, inputDTO);
    }

    @Test
    void update_WhenEnrollmentNotExists_ShouldReturnNotFound() {
        // Given
        EnrollmentDTO inputDTO = new EnrollmentDTO();
        inputDTO.setStatus("COMPLETED");
        when(enrollmentService.update(999L, inputDTO)).thenThrow(new RuntimeException("Enrollment not found"));

        // When
        ResponseEntity<EnrollmentDTO> response = enrollmentController.update(999L, inputDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(enrollmentService).update(999L, inputDTO);
    }

    @Test
    void delete_WhenEnrollmentExists_ShouldReturnNoContent() {
        // Given
        doNothing().when(enrollmentService).delete(1L);

        // When
        ResponseEntity<Void> response = enrollmentController.delete(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(enrollmentService).delete(1L);
    }

    @Test
    void delete_WhenEnrollmentNotExists_ShouldReturnNotFound() {
        // Given
        doThrow(new RuntimeException("Enrollment not found")).when(enrollmentService).delete(999L);

        // When
        ResponseEntity<Void> response = enrollmentController.delete(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(enrollmentService).delete(999L);
    }
}