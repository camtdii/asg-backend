package th.mfu.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private Integer credits = 3;

    // Constructors
    public CourseDTO() {}

    public CourseDTO(Long id, String name, String description, Integer credits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.credits = credits != null ? credits : 3;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
}