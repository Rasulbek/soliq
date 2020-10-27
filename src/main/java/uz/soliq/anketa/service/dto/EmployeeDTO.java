package uz.soliq.anketa.service.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link uz.soliq.anketa.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String fullName;

    private String email;

    private String phoneNumber;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private String nation;

    @Lob
    private byte[] photo;

    private Set<AcademicDegreeDTO> universities = new HashSet<>();

    private Set<JobHistoryDTO> employers = new HashSet<>();

    private String photoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<AcademicDegreeDTO> getUniversities() {
        return universities;
    }

    public void setUniversities(Set<AcademicDegreeDTO> universities) {
        this.universities = universities;
    }

    public Set<JobHistoryDTO> getEmployers() {
        return employers;
    }

    public void setEmployers(Set<JobHistoryDTO> employers) {
        this.employers = employers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", nation='" + getNation() + "'" +
            ", photo='" + getPhoto() + "'" +
            "}";
    }
}
