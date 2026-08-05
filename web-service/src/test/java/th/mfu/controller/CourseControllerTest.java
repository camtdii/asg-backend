package th.mfu.controller;

import th.mfu.dto.CourseDTO;
import th.mfu.service.CourseService;
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
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    void create_ShouldReturnCreatedCourse() {
        // Given
        CourseDTO inputDTO = new CourseDTO();
        inputDTO.setName("Mathematics");
        inputDTO.setDescription("Advanced Math");
        inputDTO.setCredits(4);

        CourseDTO expectedDTO = new CourseDTO(1L, "Mathematics", "Advanced Math", 4);
        when(courseService.create(inputDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<CourseDTO> response = courseController.create(inputDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Mathematics", response.getBody().getName());
        verify(courseService).create(inputDTO);
    }

    @Test
    void findAll_ShouldReturnListOfCourses() {
        // Given
        List<CourseDTO> expectedCourses = Arrays.asList(
            new CourseDTO(1L, "Mathematics", "Advanced Math", 4),
            new CourseDTO(2L, "Physics", "Quantum Physics", 3)
        );
        when(courseService.findAll()).thenReturn(expectedCourses);

        // When
        ResponseEntity<List<CourseDTO>> response = courseController.findAll();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(courseService).findAll();
    }

    @Test
    void findById_WhenCourseExists_ShouldReturnCourse() {
        // Given
        CourseDTO expectedDTO = new CourseDTO(1L, "Mathematics", "Advanced Math", 4);
        when(courseService.findById(1L)).thenReturn(Optional.of(expectedDTO));

        // When
        ResponseEntity<CourseDTO> response = courseController.findById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Mathematics", response.getBody().getName());
        verify(courseService).findById(1L);
    }

    @Test
    void findById_WhenCourseNotExists_ShouldReturnNotFound() {
        // Given
        when(courseService.findById(999L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<CourseDTO> response = courseController.findById(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(courseService).findById(999L);
    }

    @Test
    void update_WhenCourseExists_ShouldReturnUpdatedCourse() {
        // Given
        CourseDTO inputDTO = new CourseDTO();
        inputDTO.setName("Mathematics Updated");
        inputDTO.setDescription("Updated Description");
        inputDTO.setCredits(5);

        CourseDTO expectedDTO = new CourseDTO(1L, "Mathematics Updated", "Updated Description", 5);
        when(courseService.update(1L, inputDTO)).thenReturn(expectedDTO);

        // When
        ResponseEntity<CourseDTO> response = courseController.update(1L, inputDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Mathematics Updated", response.getBody().getName());
        verify(courseService).update(1L, inputDTO);
    }

    @Test
    void update_WhenCourseNotExists_ShouldReturnNotFound() {
        // Given
        CourseDTO inputDTO = new CourseDTO();
        inputDTO.setName("Mathematics Updated");
        when(courseService.update(999L, inputDTO)).thenThrow(new RuntimeException("Course not found"));

        // When
        ResponseEntity<CourseDTO> response = courseController.update(999L, inputDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(courseService).update(999L, inputDTO);
    }

    @Test
    void delete_WhenCourseExists_ShouldReturnNoContent() {
        // Given
        doNothing().when(courseService).delete(1L);

        // When
        ResponseEntity<Void> response = courseController.delete(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService).delete(1L);
    }

    @Test
    void delete_WhenCourseNotExists_ShouldReturnNotFound() {
        // Given
        doThrow(new RuntimeException("Course not found")).when(courseService).delete(999L);

        // When
        ResponseEntity<Void> response = courseController.delete(999L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(courseService).delete(999L);
    }
}