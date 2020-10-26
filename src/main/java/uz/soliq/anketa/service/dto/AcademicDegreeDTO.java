package uz.soliq.anketa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import uz.soliq.anketa.domain.enumeration.Degree;

/**
 * A DTO for the {@link uz.soliq.anketa.domain.AcademicDegree} entity.
 */
public class AcademicDegreeDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 250)
    private String institutionName;

    @NotNull
    @Size(max = 250)
    private String discipline;

    @NotNull
    @Min(value = 1950)
    @Max(value = 2050)
    private Integer startYear;

    @Min(value = 1950)
    @Max(value = 2050)
    private Integer endYear;

    @NotNull
    private Degree obtainedDegree;


    private Long employeeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Degree getObtainedDegree() {
        return obtainedDegree;
    }

    public void setObtainedDegree(Degree obtainedDegree) {
        this.obtainedDegree = obtainedDegree;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicDegreeDTO)) {
            return false;
        }

        return id != null && id.equals(((AcademicDegreeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicDegreeDTO{" +
            "id=" + getId() +
            ", institutionName='" + getInstitutionName() + "'" +
            ", discipline='" + getDiscipline() + "'" +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            ", obtainedDegree='" + getObtainedDegree() + "'" +
            ", employeeId=" + getEmployeeId() +
            "}";
    }
}
