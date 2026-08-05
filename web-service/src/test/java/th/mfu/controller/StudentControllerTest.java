package th.mfu.controller;

import th.mfu.dto.StudentDTO;
import th.mfu.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void create_ShouldReturnCreatedStudent() {
        // Given
        StudentDTO inputDTO = new StudentDTO();
        inputDTO.setName("John Doe");
        inputDTO.setEmail("john@example.com");

        StudentDTO expectedDTO = new StudentDTO(1L, "John Doe", "john@example.com");
        when(studentService.create(inputDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<StudentDTO> response = studentController.create(inputDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john@example.com", response.getBody().getEmail());
        verify(studentService).create(inputDTO);
    }

    @Test
    void findAll_ShouldReturnListOfStudents() {
        // Given
        List<StudentDTO> expectedStudents = Arrays.asList(
            new StudentDTO(1L, "John Doe", "john@example.com"),
            new StudentDTO(2L, "Jane Smith", "jane@example.com")
        );
        when(studentService.findAll()).thenReturn(expectedStudents);

        // When
        ResponseEntity<List<StudentDTO>> response = studentController.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(studentService).findAll();
    }

    @Test
    void findById_WhenStudentExists_ShouldReturnStudent() {
        // Given
        StudentDTO expectedDTO = new StudentDTO(1L, "John Doe", "john@example.com");
        when(studentService.findById(1L)).thenReturn(Optional.of(expectedDTO));

        // When
        ResponseEntity<StudentDTO> response = studentController.findById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(studentService).findById(1L);
    }

    @Test
    void findById_WhenStudentNotExists_ShouldReturnNotFound() {
        // Given
        when(studentService.findById(999L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<StudentDTO> response = studentController.findById(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(studentService).findById(999L);
    }

    @Test
    void update_WhenStudentExists_ShouldReturnUpdatedStudent() {
        // Given
        StudentDTO inputDTO = new StudentDTO();
        inputDTO.setName("John Updated");
        inputDTO.setEmail("john.updated@example.com");

        StudentDTO expectedDTO = new StudentDTO(1L, "John Updated", "john.updated@example.com");
        when(studentService.update(1L, inputDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<StudentDTO> response = studentController.update(1L, inputDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Updated", response.getBody().getName());
        verify(studentService).update(1L, inputDTO);
    }

    @Test
    void update_WhenStudentNotExists_ShouldReturnNotFound() {
        // Given
        StudentDTO inputDTO = new StudentDTO();
        inputDTO.setName("John Updated");
        when(studentService.update(999L, inputDTO)).thenThrow(new RuntimeException("Student not found"));

        // When
        ResponseEntity<StudentDTO> response = studentController.update(999L, inputDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(studentService).update(999L, inputDTO);
    }

    @Test
    void delete_WhenStudentExists_ShouldReturnNoContent() {
        // Given
        doNothing().when(studentService).delete(1L);

        // When
        ResponseEntity<Void> response = studentController.delete(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService).delete(1L);
    }

    @Test
    void delete_WhenStudentNotExists_ShouldReturnNotFound() {
        // Given
        doThrow(new RuntimeException("Student not found")).when(studentService).delete(999L);

        // When
        ResponseEntity<Void> response = studentController.delete(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(studentService).delete(999L);
    }
}