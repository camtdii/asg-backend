package th.mfu.dto;

import javax.validation.constraints.Size;

public class ProfileDTO {

    private Long id;

    @Size(max = 255, message = "Bio must not exceed 255 characters")
    private String bio;

    @Size(max = 50, message = "Phone must not exceed 50 characters")
    private String phone;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;

    // Constructors
    public ProfileDTO() {}

    public ProfileDTO(Long id, String bio, String phone, String address) {
        this.id = id;
        this.bio = bio;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}