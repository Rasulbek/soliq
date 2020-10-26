package uz.soliq.anketa.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link uz.soliq.anketa.domain.JobHistory} entity.
 */
public class JobHistoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 250)
    private String employer;

    @NotNull
    @Size(max = 250)
    private String jobTitle;

    @NotNull
    @Min(value = 1950)
    @Max(value = 2020)
    private Integer startYear;

    @Min(value = 1950)
    @Max(value = 2050)
    private Integer endYear;


    private Long employeeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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
        if (!(o instanceof JobHistoryDTO)) {
            return false;
        }

        return id != null && id.equals(((JobHistoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobHistoryDTO{" +
            "id=" + getId() +
            ", employer='" + getEmployer() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            ", employeeId=" + getEmployeeId() +
            "}";
    }
}
