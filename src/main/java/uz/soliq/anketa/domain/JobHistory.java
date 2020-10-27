package uz.soliq.anketa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A JobHistory.
 */
@Entity
@Table(name = "job_history")
public class JobHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "employer", length = 250, nullable = false)
    private String employer;

    @NotNull
    @Size(max = 250)
    @Column(name = "job_title", length = 250, nullable = false)
    private String jobTitle;

    @NotNull
    @Min(value = 1950)
    @Max(value = 2020)
    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @Min(value = 1950)
    @Max(value = 2050)
    @Column(name = "end_year")
    private Integer endYear;

    @ManyToOne
    @JsonIgnoreProperties(value = "employers", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployer() {
        return employer;
    }

    public JobHistory employer(String employer) {
        this.employer = employer;
        return this;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public JobHistory jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public JobHistory startYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public JobHistory endYear(Integer endYear) {
        this.endYear = endYear;
        return this;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Employee getEmployee() {
        return employee;
    }

    public JobHistory employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobHistory)) {
            return false;
        }
        return id != null && id.equals(((JobHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobHistory{" +
            "id=" + getId() +
            ", employer='" + getEmployer() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            "}";
    }
}
