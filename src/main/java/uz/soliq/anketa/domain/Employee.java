package uz.soliq.anketa.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "full_name", length = 250, nullable = false)
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @NotNull
    @Column(name = "nation", nullable = false)
    private String nation;


    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.ALL})
    private Set<JobHistory> employers = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = {CascadeType.ALL})
    private Set<AcademicDegree> universities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public Employee fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Employee birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public Employee nation(String nation) {
        this.nation = nation;
        return this;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Employee photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Employee photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Set<JobHistory> getEmployers() {
        return employers;
    }

    public Employee employers(Set<JobHistory> jobHistories) {
        this.employers = jobHistories;
        return this;
    }

    public Employee addEmployer(JobHistory jobHistory) {
        this.employers.add(jobHistory);
        jobHistory.setEmployee(this);
        return this;
    }

    public Employee removeEmployer(JobHistory jobHistory) {
        this.employers.remove(jobHistory);
        jobHistory.setEmployee(null);
        return this;
    }

    public void setEmployers(Set<JobHistory> jobHistories) {
        this.employers = jobHistories;
    }

    public Set<AcademicDegree> getUniversities() {
        return universities;
    }

    public Employee universities(Set<AcademicDegree> academicDegrees) {
        this.universities = academicDegrees;
        return this;
    }

    public Employee addUniversity(AcademicDegree academicDegree) {
        this.universities.add(academicDegree);
        academicDegree.setEmployee(this);
        return this;
    }

    public Employee removeUniversity(AcademicDegree academicDegree) {
        this.universities.remove(academicDegree);
        academicDegree.setEmployee(null);
        return this;
    }

    public void setUniversities(Set<AcademicDegree> academicDegrees) {
        this.universities = academicDegrees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", nation='" + getNation() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            "}";
    }
}
